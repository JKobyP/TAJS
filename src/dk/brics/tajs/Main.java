/*
 * Copyright 2009-2015 Aarhus University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dk.brics.tajs;

import dk.brics.tajs.analysis.Analysis;
import dk.brics.tajs.analysis.StaticDeterminacyContextSensitivityStrategy;
import dk.brics.tajs.flowgraph.*;
import dk.brics.tajs.flowgraph.JavaScriptSource.Kind;
import dk.brics.tajs.htmlparser.HTMLParser;
import dk.brics.tajs.js2flowgraph.FlowGraphBuilder;
import dk.brics.tajs.lattice.*;
import dk.brics.tajs.monitoring.AnalysisPhase;
import dk.brics.tajs.monitoring.IAnalysisMonitoring;
import dk.brics.tajs.monitoring.Monitoring;
import dk.brics.tajs.monitoring.TypeCollector;
import dk.brics.tajs.options.ExperimentalOptions;
import dk.brics.tajs.options.Options;
import dk.brics.tajs.solver.Message;
import dk.brics.tajs.solver.SolverSynchronizer;
import dk.brics.tajs.util.AnalysisException;
import dk.brics.tajs.util.Loader;
import dk.brics.tajs.util.Strings;
import net.htmlparser.jericho.Source;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import dk.brics.tajs.lattice.State;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;

import static dk.brics.tajs.util.Collections.newList;
import static dk.brics.tajs.util.Collections.newSet;

//-------------------------------------------------
import edu.oakland.stringabs.*;
import org.apache.tools.ant.filters.StringInputStream;
//-------------------------------------------------

/**
 * Main class for the TAJS program analysis.
 */
public class Main {

    private static Logger log = Logger.getLogger(Main.class);

    private Main() {//empty main
    }

    /**
     * Runs the analysis on the given source files.
     * Run without arguments to see the usage.
     * Terminates with System.exit.
     */
    public static void main(String[] args) {

        String callgraph = "-callgraph"; // output call graph as text and in a file `out/callgraph.dot` (process with [Graphviz dot](http://www.graphviz.org/))
        String collect_variable_info = "-collect-variable-info"; // output type and line information about all variables
        String debug = "-debug"; // output extensive internal information during the analysis
        String flowgraph = "-flowgraph"; //output the initial and final flow graphs (TAJS's intermediate representation) as text and to `out/flowgraphs/`
                                        // (in Graphviz dot format, with a file for each function and for the complete program)
        String low_severity = "-low-severity"; // enable many more type warnings
        String quiet = "-quiet"; // only print results, not information about analysis progress
        String states = "-states"; // output intermediate abstract states during the analysis
        String uneval = "-uneval"; // enable the Unevalizer for on-the-fly translation of `eval` calls, as described in
                                    // 'Remedying the Eval that Men Do', ISSTA 2012
        String determinacy = "-determinacy"; // enable the techniques described in 'Determinacy in Static Analysis of jQuery', OOPSLA 2014
        //***************** file path
        String path = "C:/Users/reu-5/dev/repo/TAJS/test/koby/test.js";
        String[] file = {path,
                //"-statistics",
                collect_variable_info,
//                debug,
                flowgraph
//                "-no-for-in"
//                "-propagate-dead-flow"
        };
        Options.get().enableTest();
        /**************************/

        try {
            initLogging();

            Analysis a = init(file, null);

            if (a == null)
                System.exit(-1);
            run(a);

            // Kasdl -- test The analysis ***********************************************************************************************************>>>>>>>>
            //testAnalysis(a);

            //ToInterval intervale = new ToInterval();
           // String res = intervale.printList();
           // System.out.println("^^^^^^^^^^^^^^^^^^^^\n\n\n" + res);
           // ToInterval.make();

            System.exit(0);
        } catch (AnalysisException e) {
            e.printStackTrace();
            System.exit(-2);
        }
//        catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

    }

    /**
     * Resets all internal counters and caches.
     */
    public static void reset() {
        StaticDeterminacyContextSensitivityStrategy.SyntacticHints.reset();
        ExperimentalOptions.ExperimentalOptionsManager.reset();
        Options.reset();
        State.reset();
        Value.reset();
        Obj.reset();
        Strings.reset();
        ScopeChain.reset();
    }

    /**
     * Reads the input and prepares an analysis object, using the default monitoring.
     */
    public static Analysis init(String[] args, SolverSynchronizer sync) throws AnalysisException {
        return init(args, new Monitoring(), sync);
    }

    /**
     * Reads the input and prepares an analysis object.
     *
     * @return analysis object, null if invalid input
     * @throws AnalysisException if internal error
     */
    public static Analysis init(String[] args, IAnalysisMonitoring monitoring, SolverSynchronizer sync) throws AnalysisException {
//        System.out.println("=========== KasdL ===========");
//        for (int index = 0; index < args.length; ++index)
//		        {
//		            System.out.println("args[" + index + "]: " + args[index]);
//		        }

		boolean show_usage = false;
        Options.parse(args);
        Options.get().checkConsistency();
        List<String> files = Options.get().getArguments();

//        "=========== KasdL ==========="
//        System.out.println(Options.get().getArguments());
//        System.exit(0);

        Analysis analysis = new Analysis(monitoring, sync);

        if (files.isEmpty()) {
            System.out.println("No source files");
            show_usage = true;
        }
        //show_usage = true;
        if (show_usage) {
            System.out.println("TAJS - Type Analyzer for JavaScript");
            System.out.println("Copyright 2009-2015 Aarhus University\n");
            System.out.println("Usage: java -jar tajs-all.jar [OPTION]... [FILE]...\n");
            Options.get().describe();
            return null;
        }
        if (Options.get().isDebugEnabled())
            Options.dump();

        enterPhase(AnalysisPhase.LOADING_FILES, analysis.getMonitoring());
        Source document = null;
        FlowGraph fg;
        try {
            // split into JS files and HTML files
            String html_file = null;
            List<String> js_files = newList();
            for (String fn : files) {
                String l = fn.toLowerCase();
                if (l.endsWith(".html") || l.endsWith(".xhtml") || l.endsWith(".htm")) {
                    if (html_file != null)
                        throw new AnalysisException("Only one HTML file can be analyzed at a time.");
                    html_file = fn;
                } else
                    js_files.add(fn);
            }
            FlowGraphBuilder builder = new FlowGraphBuilder(String.join(",", files));
            builder.transformHostFunctionSources(HostEnvSources.get());
            if (!js_files.isEmpty()) {
                if (html_file != null)
                    throw new AnalysisException("Cannot analyze a HTML file and JavaScript files at the same time.");
                // build flowgraph for JS files
                for (String js_file : js_files) {
                    if (!Options.get().isQuietEnabled())
                        log.info("Loading " + js_file);
                    builder.transformStandAloneCode(JavaScriptSource.makeFileCode(js_file, Loader.getString(js_file, "UTF-8")));
                }
            } else {
                // build flowgraph for JavaScript code in or referenced from HTML file
                Options.get().enableIncludeDom(); // always enable DOM if any HTML files are involved
                if (!Options.get().isQuietEnabled())
                    log.info("Loading " + html_file);
                HTMLParser p = new HTMLParser(Paths.get(html_file));
                document = p.getHTML();
                for (JavaScriptSource js : p.getJavaScript()) {
                    if (!Options.get().isQuietEnabled() && js.getKind() == Kind.FILE)
                        log.info("Loading " + js.getFileName());
                    builder.transformWebAppCode(js);
                }
            }
            fg = builder.close();
            //********** @ this point FlowGraph is built kasdl*************

            //************ kasdl - testing the fg
            //test(fg);
            // **********************

        } catch (IOException e) {
            log.error("Unable to parse " + e.getMessage());
            return null;
        }
        leavePhase(AnalysisPhase.LOADING_FILES, analysis.getMonitoring());
        if (sync != null)
            sync.setFlowGraph(fg);
        if (Options.get().isFlowGraphEnabled())
            dumpFlowGraph(fg, false);

        analysis.getSolver().init(fg, document);

        monitoring.setFlowgraph(analysis.getSolver().getFlowGraph());
        monitoring.setCallGraph(analysis.getSolver().getAnalysisLatticeElement().getCallGraph());
        return analysis;
    }


    /**
     * Configures log4j.
     */
    public static void initLogging() {
        Properties prop = new Properties(); // This is java.util.Properties
        prop.put("log4j.rootLogger", "INFO, tajs"); // DEBUG / INFO / WARN / ERROR
        prop.put("log4j.appender.tajs", "org.apache.log4j.ConsoleAppender");
        prop.put("log4j.appender.tajs.layout", "org.apache.log4j.PatternLayout");
        prop.put("log4j.appender.tajs.layout.ConversionPattern", "%m%n");
        PropertyConfigurator.configure(prop);
        //System.out.println("Properties print"+prop);
    }

    /**
     * Runs the analysis.
     *
     * @throws AnalysisException if internal error
     */
    public static void run(Analysis analysis) throws AnalysisException {
        IAnalysisMonitoring monitoring = analysis.getMonitoring();

        long time = System.currentTimeMillis();

        enterPhase(AnalysisPhase.DATAFLOW_ANALYSIS, monitoring);
        analysis.getSolver().solve();
        leavePhase(AnalysisPhase.DATAFLOW_ANALYSIS, monitoring);

        long elapsed = System.currentTimeMillis() - time;
        if (Options.get().isTimingEnabled())
            log.info("Analysis finished in " + elapsed + "ms");

        if (Options.get().isFlowGraphEnabled())
            dumpFlowGraph(analysis.getSolver().getFlowGraph(), true);

        enterPhase(AnalysisPhase.SCAN, monitoring);
        analysis.getSolver().scan();
        leavePhase(AnalysisPhase.SCAN, monitoring);
    }

    /**
     * Outputs the flowgraph (in graphviz dot files).
     */
    private static void dumpFlowGraph(FlowGraph g, boolean end) {
        try {
            // create directories
            File outdir = new File("out");
            if (!outdir.exists()) {
                outdir.mkdir();
            }
            String path = "out" + File.separator + "flowgraphs";
            File outdir2 = new File(path);
            if (!outdir2.exists()) {
                outdir2.mkdir();
            }
            // dump the flowgraph to file
            try (PrintWriter pw = new PrintWriter(new FileWriter(path + File.separator + (end ? "final" : "initial") + ".dot"))) {
                g.toDot(pw);
            } catch (IOException e) {
                throw new AnalysisException(e);
            }
            // dump each function to file
            g.toDot(path, end);
            // also print flowgraph
            log.info(g.toString());
        } catch (IOException e) {
            throw new AnalysisException(e);
        }
    }

    private static void enterPhase(AnalysisPhase phase, IAnalysisMonitoring monitoring) {
        String phaseName = prettyPhaseName(phase);
        showPhaseStart(phaseName);
        monitoring.beginPhase(phase);
    }

    private static void showPhaseStart(String phaseName) {
        if (!Options.get().isQuietEnabled()) {
            log.info("===========  " + phaseName + " ===========");
        }
    }

    private static void leavePhase(AnalysisPhase phase, IAnalysisMonitoring monitoring) {
        monitoring.endPhase(phase);
    }

    private static String prettyPhaseName(AnalysisPhase phase) {
        switch (phase) {
            case LOADING_FILES:
                return "Loading files";
            case DATAFLOW_ANALYSIS:
                return "Data flow analysis";
            case SCAN:
                return "Scan";
            default:
                throw new RuntimeException("Unhandled phase enum: " + phase);
        }
    }


//    // Kasdl -- My Functions  ***********************************************************************************************************>>>>>>>>
//
//    /**
//     * TO test the analysis object.
//     * @param m the Analysis Object
//     * @throws FileNotFoundException
//     * @throws UnsupportedEncodingException
//     */
//    private static void testAnalysis(Analysis m) throws FileNotFoundException, UnsupportedEncodingException {
//
//        IAnalysisMonitoring f = m.getMonitoring();
//        monitoringTest(f);
//
//        FlowGraph fg = m.getSolver().getFlowGraph();
//        flowGraphTest(fg);
//
//        Map<TypeCollector.VariableSummary, Value> ia = f.getTypeInformation(); // Map variable --> value
//        printTheMap(ia);
//
//        //System.exit(0);
//    }
//
//    /**
//     * to print out the partial map - var -> value
//     * @param vs the variable map
//     */
//    private static void printTheMap(Map<TypeCollector.VariableSummary, Value> vs) {
//
//        System.out.println("\nPrint out the Monitoring Map : ");
//
//        for (Map.Entry<TypeCollector.VariableSummary, Value> entry : vs.entrySet()) {
//            String key = entry.getKey().getVariableName();
//            Value value = entry.getValue();
//            System.out.println();
//            System.out.println("key : " + key + "\n" + "value : " + value);
//            //System.out.println("prefix    : " +value.isMaybeSingleNum());
//        }
//    }
//
//    /**
//     * To print out the flow graph and all the functions, blocks
//     * ,and the abstract nodes
//     * @param fg FlowGraph
//     */
//    private static void flowGraphTest(FlowGraph fg) {
//        Set<String> FunVarName;
//        System.out.println("+++++++++++++++++++++++++++++++++");
//        System.out.println("Print the fg From Main:\n"+fg);
//        System.out.println("+++++++++++++++++++++++++++++++++");
//
//        for (Function fun : fg.getFunctions()) {
//            FunVarName = fun.getVariableNames();
//            for(String varnam : FunVarName)
//                System.out.println("varnam    : " + varnam);
//            //System.out.println(fun.getSourceLocation().getLineNumber());
//            System.out.println("\nFunction : " + fun.getName());
//            for (BasicBlock b : fun.getBlocks()) {
//                for (AbstractNode an : b.getNodes()) {
//                    //System.out.println(an.getClass());
//                    System.out.println("AbstractNode : " + an.toString());
//                }
//                System.out.println("---------" );// end of block
//            }
//            System.out.println("+++++++++++ End of Function" );// end of function
//
//        }
//    }
//
//    /**
//     * To print out the messages
//     * @param m IAnalysisMonitoring object
//     */
//    public static void monitoringTest(IAnalysisMonitoring m){
//        System.out.println("\n+++++++++++++++++++++++++++++++++");
//        System.out.println("Print the Monitoring From Main:");
//        System.out.println("+++++++++++++++++++++++++++++++++");
//        System.out.println("\nPrint out the Monitoring messages : ");
//        for(Message message  : m.getMessages()){
//            System.out.println(message.getMessage());
//        }
//    }
}
