package dk.brics.tajs.test;

import dk.brics.tajs.Main;
import dk.brics.tajs.options.Options;
import dk.brics.tajs.util.AnalysisException;
import dk.brics.tajs.util.AnalysisLimitationException;
import dk.brics.tajs.util.ParseError;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.fail;

@SuppressWarnings("static-method")
public class TestMicro {

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main("dk.brics.tajs.test.TestMicro");
	}

	@Before
	public void init() {
		Main.reset();
		Options.get().enableTest();
		Options.get().enableContextSensitiveHeap();
		Options.get().enableParameterSensitivity();
		//Options.get().enableForInSpecialization();
		//Options.get().enableNoPolymorphic();
		//Options.get().enableNoModified();
		//Options.get().enableNoLazy();
		//Options.get().enableDebug();
		//Options.get().enableNoRecency();
	}

	@Test
	public void micro_00() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test00.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_01() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test01.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_02() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test02.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_03() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test03.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_04() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test04.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_05() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test05.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_06() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test06.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_07() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test07.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_08() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test08.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_09() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test09.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_10() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test10.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_11() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test11.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_12() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test12.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_13() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test13.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_14() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test14.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_15() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test15.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_16() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test16.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_17() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test17.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_18() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test18.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_19() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test19.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_20() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test20.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_21() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test21.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_22() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test22.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_23() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test23.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_24() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test24.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_25() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test25.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_26() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test26.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_27() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test27.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_28() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test28.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_29() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test29.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_30() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test30.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_31() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test31.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_32() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test32.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_33() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test33.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_34() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test34.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_35() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test35.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_36() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test36.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_37() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test37.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_38() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test38.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_39() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test39.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_40() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test40.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_41() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test41.js", "test/micro/test41b.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_42() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test42.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_43() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test43.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_44() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test44.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_45() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test45.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_46() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test46.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_47() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test47.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_48() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test48.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_49() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test49.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Ignore // Picker & Maldonado
	@Test
	public void micro_50() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test50.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_51() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test51.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_52() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test52.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_53() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test53.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_54() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test54.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_55() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test55.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_56() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test56.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_57() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test57.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_58() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test58.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_59() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test59.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_60() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test60.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_61() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test61.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_62() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test62.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_63() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test63.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_64() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test64.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_65() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test65.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_66() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test66.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_67() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test67.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_68() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test68.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_69() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test69.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_70() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test70.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_71() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test71.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_72() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test72.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_73() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test73.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

    @Test
    public void micro_73simple() throws Exception {
        Options.get().enableFlowgraph();
        Misc.init();
        Misc.captureSystemOutput();
        String[] args = {"test/micro/test73simple.js"};
        Misc.run(args);
        Misc.checkSystemOutput();
    }

	// Picker & Maldonado
	@Ignore
	@Test
	public void micro_74() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test74.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_75() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test75.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_76() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test76.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_77() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test77.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_78() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test78.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_79() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test79.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_80() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test80.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_81() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test81.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_82() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test82.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_83() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test83.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_84() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test84.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_85() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test85.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_86() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test86.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_87() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test87.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_88() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test88.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_89() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test89.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_90() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test90.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_91() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test91.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_92() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test92.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_93() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test93.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_94() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test94.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_95() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test95.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_96() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test96.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_97() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test97.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_98() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test98.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_99() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test99.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_100() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test100.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test(expected = AssertionError.class /* GitHub #36 */)
	public void micro_101() throws Exception {
		Misc.init();
		String[] args = {"test/micro/test101.js"};
		Misc.run(args);
	}

	@Test
	public void micro_102() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test102.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_103() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test103.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_104() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test104.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_105() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test105.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_106() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test106.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_107() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test107.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_108() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test108.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_109() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test109.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_110() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test110.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_111() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test111.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_112() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test112.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_113() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test113.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test(expected = ParseError.class)
	public void micro_114() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test114.js"}; // Should be runtime error, but Sec 16 p.149 allows us to report the error early
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_115() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test115.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_116() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test116.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_117() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test117.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_118() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test118.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_119() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test119.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_120() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test120.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_121() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test121.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_122() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test122.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_123() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test123.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test(expected = AssertionError.class /* GitHub #191 */)
	public void micro_125() throws Exception {
		Misc.init();
		String[] args = {"test/micro/test125.js"};
		Misc.run(args);
	}

	@Test
	public void micro_125a() throws Exception {
		Misc.init();
		String[] args = {"test/micro/test125a.js"};
		Misc.run(args);
	}

	@Test
	public void micro_125b() throws Exception {
		Misc.init();
		String[] args = {"test/micro/test125b.js"};
		Misc.run(args);
	}

	@Test
	public void micro_125c () throws Exception {
		Misc.init();
		String[] args = {"test/micro/test125c.js"};
		Misc.run(args);
	}

	@Test(expected = AssertionError.class /* GitHub #191 */)
	public void micro_125d () throws Exception {
		Misc.init();
		String[] args = {"test/micro/test125d.js"};
		Misc.run(args);
	}

	@Test
	public void micro_126() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test126.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_127() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test127.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_128() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test128.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_129() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test129.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_130() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test130.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_131() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test131.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_132() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test132.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_133() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test133.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_134() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test134.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_135() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test135.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_136() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test136.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_137() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test137.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_138() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test138.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_139() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test139.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test(expected = AnalysisException.class /* top level return */)
	public void micro_140a() throws Exception {
		Misc.init();
		String[] args = {"test/micro/test140.js"};
		Misc.run(args);
	}

	@Test(expected = AnalysisException.class /* top level return */)
	public void micro_140b() throws Exception {
		Misc.init();
		Options.get().enableIncludeDom();
		String[] args = {"test/micro/test140.js"};
		Misc.run(args);
	}

	@Test
	public void micro_141() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test141.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_142() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test142.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test(expected = AssertionError.class /* GitHub #3 */)
	public void micro_143() throws Exception {
		fail("Add support for getters/setters");
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test143.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test(expected = AssertionError.class /* GitHub #3 */)
	public void micro_144() throws Exception {
		fail("Add support for getters/setters");
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test144.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_145() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test145.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test(expected = AssertionError.class /* GitHub #191 */)
	public void micro_146() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test146.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_147() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test147.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_148() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test148.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_148b() throws Exception {
		Options.get().enableNoModified(); // maybe-modified can make a difference, even with lazy prop
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test148.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_149() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test149.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_150() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test150.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_151() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test151.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_152() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test152.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_153() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test153.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_154() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test154.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_155() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test155.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_156() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test156.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_157() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test157.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_158() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test158.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_159() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test159.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_160() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test160.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_161() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test161.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_162() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test162.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_163() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test163.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_164() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test164.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_165() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test165.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_166() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test166.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_167() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test167.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_168() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test168.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_169() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test169.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_170() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test170.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_171() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test171.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_172() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test172.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_173() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test173.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_174() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test174.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_175() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test175.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}
	@Test
	public void micro_176() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test176.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_177() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test177.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_178() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test178.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test(expected = AssertionError.class /* GitHub #191 */)
	public void micro_179() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test179.js"};
		Misc.run(args);
		fail(); // Misc.checkSystemOutput();
	}

	@Test
	public void micro_180() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test180.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_181() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test181.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_182() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test182.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_183() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test183.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_184() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test184.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test(expected = AssertionError.class /* GitHub #243 */)
	public void micro_185() throws Exception {
		Misc.init();
		String[] args = {"test/micro/test185.js"};
		Misc.run(args);
	}

	@Test(expected = AssertionError.class /* GitHub #244 */)
	public void micro_186() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test186.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_187() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test187.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_188() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test188.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_189() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test189.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_190() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test190.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_191() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test191.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_192() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test192.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_200() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test200.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Ignore // Picker & Maldonado
	@Test
	public void micro_201() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test201.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Ignore // Picker & Maldonado
	@Test
	public void micro_202() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test202.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_203() throws Exception {
		Misc.init();
		Options.get().enableLoopUnrolling(100);
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test203.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_204() throws Exception {
		Misc.init();
		Options.get().enableLoopUnrolling(100);
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test204.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_205() throws Exception {
		Misc.init();
		String[] args = {"test/micro/test205.js"};
		Misc.run(args);
	}

	@Ignore // Picker & Maldonado
	@Test
	public void micro_206() throws Exception {
		Misc.init();
		Options.get().enableLoopUnrolling(100);
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test206.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_207() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test207.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_208() throws Exception {
		Misc.init();
		Options.get().enableNoPolymorphic();
		Options.get().enableNoChargedCalls();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test208.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_209() throws Exception {
		Misc.init();
		Options.get().enableNoPolymorphic();
		Options.get().enableNoChargedCalls();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test209.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_210() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test210.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_210b() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/test210b.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_testArray() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/testArray.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_testBoolean() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/testBoolean.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_testEval() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		Options.get().enableUnevalizer();
		String[] args = {"test/micro/testEval.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_testFunction() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		Options.get().enableUnevalizer();
		String[] args = {"test/micro/testFunction.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_testFunctionApply() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/testFunctionApply.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_testFunctionCall() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/testFunctionCall.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_testNumber() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/testNumber.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_testObject() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/testObject.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_testOO() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/testOO.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_testRegExp() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/testRegExp.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_testString() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/testString.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_testToPrimitive() throws Exception {
		Misc.init();
		String[] args = {"test/micro/testToPrimitive.js"};
		Misc.run(args);
	}

	@Test
	public void micro_testPaper() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/testPaper.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Ignore // Picker & Maldonado
	@Test
	public void micro_testForIn1() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/testForIn1.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Ignore // Picker & Maldonado
	@Test
	public void micro_testForIn2() throws Exception {
		Misc.init();
        Misc.captureSystemOutput();
		String[] args = {"test/micro/testForIn2.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Ignore // Picker & Maldonado
    @Test
    public void micro_testForIn2simple() throws Exception {
        Misc.init();
        Misc.captureSystemOutput();
        String[] args = {"test/micro/testForIn2simple.js"};
        Misc.run(args);
        Misc.checkSystemOutput();
    }

	@Ignore // Picker & Maldonado
	@Test
	public void micro_testForIn3() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = { "test/micro/testForIn3.js" };
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Ignore // Picker & Maldonado
	@Test
	public void micro_testForIn4() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = { "test/micro/testForIn4.js" };
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Ignore // Picker & Maldonado
	@Test
	public void micro_testForIn5() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = { "test/micro/testForIn5.js" };
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Ignore // Picker & Maldonado
	@Test
	public void micro_testForIn6() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = { "test/micro/testForIn6.js" };
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Ignore // Picker & Maldonado
	@Test
	public void micro_testForIn7() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = { "test/micro/testForIn7.js" };
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Ignore // Picker & Maldonado
    @Test
    public void micro_testForIn8() throws Exception {
        Misc.init();
        Misc.captureSystemOutput();
        String[] args = { "test/micro/testForIn8.js" };
        Misc.run(args);
        Misc.checkSystemOutput();
    }

	@Ignore // Picker & Maldonado
	@Test
	public void micro_testForInHasOwn() throws Exception {
		Misc.init();
		String[] args = { "test/micro/testForInHasOwn.js" };
		Misc.run(args);
	}

	@Ignore // Picker & Maldonado
    @Test
    public void micro_testForInException() throws Exception {
        Misc.init();
        Options.get().enableFlowgraph();
        String[] args = { "test/micro/testForInException.js" };
        Misc.run(args);
    }

	@Ignore // Picker & Maldonado
	@Test
	public void micro_testForInPrototypeProperties() throws Exception {
		Misc.init();
		String[] args = { "test/micro/testForInPrototypeProperties.js" };
		Misc.run(args);
	}

	@Ignore // Picker & Maldonado
	@Test
	public void micro_testForInNoEnumerablePrototypeProperties() throws Exception {
		Misc.init();
		String[] args = { "test/micro/testForInNoEnumerablePrototypeProperties.js" };
		Misc.run(args);
	}

	@Ignore // Picker & Maldonado
    @Test
	public void micro_testForInEach() throws Exception {
		Misc.init();
		String[] args = {"test/micro/testForInEach.js"};
		Misc.run(args);
	}

	@Test
	public void micro_testCall1() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/testCall1.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_testCall2() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/testCall2.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_testDeadAssignmentsAndDPAs() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/testDeadAssignmentsAndDPAs.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_testDeadAssignmentsAndNatives() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/testDeadAssignmentsAndNatives.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_testNumAdd() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/testNumAdd.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_testNumSub() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/testNumSub.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_testUintAdd() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/testUintAdd.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_testUintSub() throws Exception {
		// XXX unsound, see reference to this test in Operators.java
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/testUintSub.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void micro_testConreteStrings() throws Exception {
		Misc.init();
		Misc.runSource(
				"TAJS_assert(/x/.test('x'));",
				"TAJS_assert(/-\\[/.test('-['));",
				"TAJS_assert(/\\[-/.test('[-'));"
		);
	}

	@Test
	public void micro_testReplaceUnclosedCharacterClass() throws Exception {
		Misc.init();
		Misc.runSource(
				"TAJS_assert('['.replace('[', 'x') === 'x');"
		);
	}

	@Test
	public void micro_escapedCharacters() throws Exception {
		Misc.init();
		String[] args = {"test/micro/escapedCharacters.js"};
		Misc.run(args);
	}

	@Test
	public void micro_testFunctionConstructor() throws Exception {
		Misc.init();
		Options.get().enableUnevalizer();
		Misc.runSource(
				"TAJS_assert((typeof new Function('')) === 'function');",
				"TAJS_assert(new Function('')() === undefined);",
				"TAJS_assert((typeof new Function('', '')) === 'function');",
				"TAJS_assert(new Function('', '')() === undefined);"
		);
	}

	@Test
	public void micro_testEmptyFunctionConstructor() throws Exception {
		Misc.init();
		Options.get().enableUnevalizer();
		Misc.runSource(
				"TAJS_assert((typeof new Function()) === 'function');",
				"TAJS_assert(new Function()() === undefined);"
		);
	}

	@Test
	public void micro_testConcreteDecodeURI() throws Exception {
		Misc.init();
		Misc.runSource(
				"TAJS_assert(decodeURI() === 'undefined');",
				"TAJS_assert(decodeURI('http://test.com/%20') === 'http://test.com/ ');"
		);
	}

	@Test
	public void micro_testConcreteDecodeURIComponent() throws Exception {
		Misc.init();
		Misc.runSource(
				"TAJS_assert(decodeURIComponent() === 'undefined');",
				"TAJS_assert(decodeURIComponent('%20') === ' ');"
		);
	}

	@Test
	public void micro_testConcreteEncodeURI() throws Exception {
		Misc.init();
		Misc.runSource(
				"TAJS_assert(encodeURI() === 'undefined');",
				"TAJS_assert(encodeURI('http://test.com/ ') === 'http://test.com/%20');"
		);
	}

	@Test
	public void micro_testConcreteEncodeURIComponent() throws Exception {
		Misc.init();
		Misc.runSource(
				"TAJS_assert(encodeURIComponent() === 'undefined');",
				"TAJS_assert(encodeURIComponent(' ') === '%20');"
		);
	}

	@Test
	public void micro_testConcreteEscape() throws Exception {
		Misc.init();
		Misc.runSource(
				"TAJS_assert(escape() === 'undefined');",
				"TAJS_assert(escape(' ') === '%20');"
		);
	}

	@Test
	public void micro_testConcreteUnescape() throws Exception {
		Misc.init();
		Misc.runSource(
				"TAJS_assert(unescape() === 'undefined');",
				"TAJS_assert(unescape('%20') === ' ');"
		);
	}

	@Test
	public void micro_testConcreteParseInt() throws Exception {
		Misc.init();
		Misc.runSource(
				"TAJS_assert(parseInt(42) === 42);",
				"TAJS_assert(parseInt('42') === 42);",
				"TAJS_assert(parseInt('0x42') === 66);",
				"TAJS_assert(parseInt('42', 5) === 22);",
				"TAJS_assert(isNaN(parseInt('x')));",
				"TAJS_assert(isNaN(parseInt(true)));"
		);
	}

	@Test
	public void micro_testConcreteParseFloat() throws Exception {
		Misc.init();
		Misc.runSource(
				"TAJS_assert(parseFloat(42) === 42);",
				"TAJS_assert(parseFloat('42') === 42);",
				"TAJS_assert(parseFloat('42.3') === 42.3);",
				"TAJS_assert(parseFloat('0x42') === 0);",
				"TAJS_assert(isNaN(parseFloat('x')));",
				"TAJS_assert(isNaN(parseFloat(true)));"
		);
	}

	@Test
	public void micro_testToString() throws Exception {
		Misc.init();
		Misc.runSource(
				"var U = !!Math.random();",
				"TAJS_assert(Object.prototype.toString.call({}) === '[object Object]');",
				"TAJS_assert(Object.prototype.toString.call([]) === '[object Array]');",
				"TAJS_assert(Object.prototype.toString.call(U? {}: {}) === '[object Object]');",
				"TAJS_assert(Object.prototype.toString.call(U? {}: []), 'isMaybeStrOther');"
		);
	}

	@Test
	public void micro_testAbstractGCPrecision() throws Exception {
			Misc.init();
			Misc.runSource(
					"function f(v){return v.p;}",
					"f({p: true})",
					"if(f()){", // definite error (mainly due to abstract GC)
					"	TAJS_assert(false);",
					"}else{",
					"	TAJS_assert(false);",
					"}",
					"TAJS_assert(false);"
			);
	}

	@Test
	public void micro_testLoopBeforeCallSequence() {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/testLoopBeforeCallSequence.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void arrayJoin() {
		Misc.init();
		Misc.runSource("",
				"var joined = ['foo',,'baz'].join();",
				"TAJS_assert(joined === 'foo,,baz');",
				""
		);
	}

	@Test
	public void micro_numberNaN() {
		Misc.init();
		Misc.runSource(
				"TAJS_assert(isNaN(NaN));",
				"TAJS_assert(isNaN(Number(NaN)));",
				"TAJS_assert(isNaN(Number('NaN')));",
				"TAJS_assert(isNaN(Number(' NaN ')));",
				"",
				"function toNumber1(val) {return Number(val);}",
				"function toNumber2(val) {return Number(val);}",
				"",
				"toNumber1(' NaN ');",
				"TAJS_assert(toNumber1('123'), 'isMaybeNaN');",
				"",
				"toNumber2('123');",
				"TAJS_assert(toNumber2(' NaN '), 'isMaybeNaN');",
				""
		);
	}

	@Test
	public void numberToString() {
		Misc.init();
		Misc.runSource("",
				"TAJS_assert('111111111111111110000' === (111111111111111111111).toString());",
				"TAJS_assert('-111111111111111110000' === (-111111111111111111111).toString());",
				"TAJS_assert('0.00001' === (0.00001).toString());",
				"TAJS_assert('1e-7' === (0.0000001).toString());",
				"");
	}

	@Test
	public void numberFormatters() {
		Misc.init();
		Misc.runSource("",
				"TAJS_assert('1.1111111111111111e+21' === (1111111111111111111111).toFixed(8));",
				"TAJS_assert('-1.1236e-4' === (-0.000112356).toExponential(4));",
				"TAJS_assert('0.000555000000000000' === (0.000555).toPrecision(15));",
				"");
	}

	@Test
	public void arrayShifting_lit() {
		Misc.init();
		Misc.runSource("",
				"var a = ['foo', 'bar'];",
				"a.shift()",
				"TAJS_assert('bar' === a[0]);",
				"");
	}

	@Test
	public void arrayShifting_pushDirect() {
		Misc.init();
		Misc.runSource("",
				"var a = [];",
				"a.push('foo');",
				"a.push('bar');",
				"a.shift()",
				"TAJS_assert('bar' === a[0]);",
				"");
	}

	@Test
	public void arrayShifting_pushIndirect() {
		Misc.init();
		Misc.runSource("",
				"function push(a, v){a.push(v);}",
				"var a = [];",
				"push(a, 'foo');",
				"push(a, 'bar');",
				"a.shift()",
				"TAJS_assert(a[0], 'isMaybeStrIdentifier');",
				"TAJS_assert(a[0], 'isMaybeUndef');",
				"");
	}

	@Test
	public void arrayProto() {
		Misc.init();
		Misc.runSource("",
				"Array.prototype[1] = 'bar';",
				"TAJS_assert([][1] === 'bar');",
				"TAJS_assert(['foo',,'baz'][1] === 'bar');",
				""
		);
	}

	@Test
	public void arrayProtoJoin1() {
		Misc.init();
		Misc.runSource("",
				"Array.prototype[1] = 'bar';",
				"TAJS_assert(['foo',,'baz'].join() === 'foo,bar,baz');",
				""
		);
	}

	@Test
	public void arrayProtoJoin2() {
		Misc.init();
		Misc.runSource("",
				"Array.prototype[1] = 'bar';",
				"var a = []",
				"a[0] = 'foo';",
				"a[2] = 'baz';",
				"TAJS_assert(a.join() === 'foo,bar,baz');",
				""
		);
	}

	@Test
	public void arrayUnshift() {
		Misc.init();
		Misc.runSource("",
				"var a = ['foo', 'bar', 'baz']",
				"a.unshift('qux')",
				"TAJS_assert(a.length === 4);",
				"TAJS_assert(a[0] === 'qux');",
				"TAJS_assert(a[1] === 'foo');",
				"TAJS_assert(a[2] === 'bar');",
				"TAJS_assert(a[3] === 'baz');",
				""
		);
	}

	@Test
	public void arraySplice() {
		Misc.init();
		Misc.runSource("",
				"var a = ['foo', 'bar', 'baz']",
				"var v = a.splice(0, 1);",
				"TAJS_assert(v[0], 'isMaybeStrIdentifier');",
				"TAJS_assert(a[0], 'isMaybeStrIdentifier');",
				"");
	}

	@Test(expected = AnalysisException.class)
	public void testLargeFunctionBody_1944_calls() throws Exception {
		Misc.init();
		// should not crash
		String[] args = {"test/micro/largeFunctionBody_1944_calls.js"};
		Misc.run(args);
	}

	@Test(expected = AnalysisException.class)
	public void testLargeFunctionBody_3888_calls() throws Exception {
		Misc.init();
		// should not crash
		String[] args = {"test/micro/largeFunctionBody_3888_calls.js"};
		Misc.run(args);
	}

	@Test
	public void infinityToStringOtherNum(){
		Misc.init();
		Misc.runSource("",
				"TAJS_assert((1/0).toString(), 'isMaybeStrOtherNum');",
				"");
	}

	@Test
	public void halfValue_toFixed(){
		Misc.init();
		Misc.runSource("",
				// NB: Nashorn produces 0 in both cases!
				"TAJS_assert((-0.5).toFixed() === '-1');",
				"TAJS_assert((0.5).toFixed() === '1');",
				"");
	}

	@Test
	public void concatNumUInts(){
		Misc.init();
		Misc.runSource("",
				"var uint = Math.random()? '0': '1';",
				"var sequenceOfDigits = uint + uint;",
				"TAJS_assert(sequenceOfDigits, 'isMaybeStrOtherNum');",
				"TAJS_assert(sequenceOfDigits + sequenceOfDigits, 'isMaybeStrIdentifierParts');",
				"");
	}

	@Test
	public void undefinedRegisterCompoundPropertyAssignmentWithCall_orig(){
		// should not crash
		Misc.init();
		Misc.runSource("",
				"var UINT = Math.random()? 0: 1",
				"var rp = [UINT];",
				"function x(i){return i;};",
				"var i = UINT;",
				"var p = [UINT]",
				"var n = UINT",
				"rp[x(i)] &= ~p[n]",
				"");
	}

	@Test
	public void undefinedRegisterCompoundPropertyAssignmentWithCall(){
		// should not crash
		Misc.init();
		Misc.runSource("",
				"var o = {};",
				"function f(){}",
				"o[f()] += 42",
				"");
	}


	@Test
	public void redefinedArrayLiteralConstructor(){
		// should not crash
		Misc.init();
		Misc.runSource("",
				"function Array(x, y, z){};",
		        "[,,,,]",
				"");
	}

	@Test
	public void arrayJoinFuzzySeparator(){
		// should not crash
		Misc.init();
		Misc.runSource("",
				"var sep = Math.random()? 'x': 'y';",
				"[1, 2, 3].join(sep);",
				"");
	}

	@Test
	public void issue197_1(){
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/issue197_1.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void issue197_2(){
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/issue197_2.html"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void stringEscapingForRegExp(){
		Misc.init();
		Misc.runSource(
				"var replaced = '['.replace('[', 'x');",
				"TAJS_assert(replaced === 'x');");
	}

	@Test
	public void eventListenerRegistrationVariants() {
		Misc.init();
		Options.get().enableIncludeDom();
		Misc.captureSystemOutput();
		Misc.runSourceWithNamedFile("eventListenerRegistrationVariants.js",
				"var element = document.getElementById('ELEMENT');",

				"window.addEventListener('click', function(){ TAJS_dumpValue('window.addEventListener(\\'click\\', ...)'); });",
				"element.addEventListener('click', function(){ TAJS_dumpValue('element.addEventListener(\\'click\\', ...)'); });",

				"window.click = function(){ TAJS_dumpValue('window.click = ...'); };",
				"element.click = function(){ TAJS_dumpValue('element.click = ...'); };",

				"window.addEventListener('onclick', function(){ TAJS_dumpValue('window.addEventListener(\\'onclick\\', ...)'); });",
				"element.addEventListener('onclick', function(){ TAJS_dumpValue('element.addEventListener(\\'onclick\\', ...)'); });",

				"window.onclick = function(){ TAJS_dumpValue('window.onclick = ...'); };",
				"element.onclick = function(){ TAJS_dumpValue('element.onclick = ...'); };",

				"window.addEventListener('mousedown', function(){ TAJS_dumpValue('window.addEventListener(\\'mousedown\\', ...)'); });",
				"element.addEventListener('mousedown', function(){ TAJS_dumpValue('element.addEventListener(\\'mousedown\\', ...)'); });",

				"window.mousedown = function(){ TAJS_dumpValue('window.mousedown = ...'); };",
				"element.mousedown = function(){ TAJS_dumpValue('element.mousedown = ...'); };",

				"window.addEventListener('onmousedown', function(){ TAJS_dumpValue('window.addEventListener(\\'onmousedown\\', ...)'); });",
				"element.addEventListener('onmousedown', function(){ TAJS_dumpValue('element.addEventListener(\\'onmousedown\\', ...)'); });",

				"window.onmousedown = function(){ TAJS_dumpValue('window.onmousedown = ...'); };",
				"element.onmousedown = function(){ TAJS_dumpValue('element.onmousedown = ...'); };",
				"");
		Misc.checkSystemOutput();
	}

	@Test
	public void eventListenerRegistrationInEventHandlerVariants() {
		Misc.init();
		Options.get().enableIncludeDom();
		Misc.captureSystemOutput();
		Misc.runSourceWithNamedFile("eventListenerRegistrationInEventHandlerVariants.js",
				"function direct_click_click_registration(){",
				"	TAJS_dumpValue('direct_click_click_registration invoked');",
				"	function indirect_click_click_registration(){ TAJS_dumpValue('indirect_click_click_registration invoked');}",
				"	window.addEventListener('click', indirect_click_click_registration);",
				"}",
				"window.addEventListener('click', direct_click_click_registration);",
				"",
				"function direct_load_load_registration(){",
				"	TAJS_dumpValue('direct_load_load_registration invoked');",
				"	function indirect_load_load_registration(){ TAJS_dumpValue('indirect_load_load_registration invoked');}",
				"	window.addEventListener('load', indirect_load_load_registration);",
				"}",
				"window.addEventListener('load', direct_load_load_registration);",
				"",
				"function direct_load_click_registration(){",
				"	TAJS_dumpValue('direct_load_click_registration invoked');",
				"	function indirect_load_click_registration(){ TAJS_dumpValue('indirect_load_click_registration invoked');}",
				"	window.addEventListener('click', indirect_load_click_registration);",
				"}",
				"window.addEventListener('load', direct_load_click_registration);",
				"",
				"function direct_click_load_registration(){",
				"	TAJS_dumpValue('direct_click_load_registration invoked');",
				"	function indirect_click_load_registration(){ TAJS_dumpValue('indirect_click_load_registration invoked');}",
				"	window.addEventListener('load', indirect_click_load_registration);", // should not trigger: load can not fire after click
				"}",
				"window.addEventListener('click', direct_click_load_registration);",
				"");
		Misc.checkSystemOutput();

	}


	@Test
	public void eventListenerRegistrationInEventHandler_sameKind() {
		Misc.init();
		Options.get().enableIncludeDom();
		Misc.captureSystemOutput();
		Misc.runSourceWithNamedFile("eventListenerRegistrationInEventHandler_sameKind.js",
				"function mousedownHandler(){",
				"	TAJS_dumpValue('mousedownHandler triggered');",
				"	addEventListener('mouseup', mouseupHandler);",
				"}",
				"function mouseupHandler(){",
				"	TAJS_dumpValue('mouseupHandler triggered');",
				"}",
				"addEventListener('mousedown', mousedownHandler);"
		);
		Misc.checkSystemOutput();

	}

	@Test
	public void eventListenerRegistrationInEventHandler_differentKind() {
		Misc.init();
		Options.get().enableIncludeDom();
		Misc.captureSystemOutput();
		Misc.runSourceWithNamedFile("eventListenerRegistrationInEventHandler_differentKind.js",
				"function mousedownHandler(){",
				"	TAJS_dumpValue('mousedownHandler triggered');",
				"	addEventListener('mouseup', keydownHandler);",
				"}",
				"function keydownHandler(){",
				"	TAJS_dumpValue('keydownHandler triggered');",
				"}",
				"addEventListener('mousedown', mousedownHandler);"
		);
		Misc.checkSystemOutput();

	}

	@Test
	public void DOM_unknownTagName(){
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/unknownTagName.html"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test(expected = AnalysisException.class)
	public void incompatibleOptions_missingArguments(){
		Misc.init();
		Misc.run(new String[]{});
	}

	@Test
	public void regexp_compile(){
		Misc.init();
		Misc.runSource(
				"var r = /x/;",
				"TAJS_assert(r.source === 'x');",
				"r.compile('y');",
				"TAJS_assert(r.source === 'y');",
				"r.compile('y', 'i');",
				"TAJS_assert(r.ignoreCase);",
				"r.compile(/z/);",
				"TAJS_assert(r.source === 'z');",
				""
		);
	}

	@Test
	public void object_create() {
		Misc.init();
		Misc.captureSystemOutput();
		Misc.runSourceWithNamedFile("object_create.js",
				"var U = !!Math.random();",
				"if(U){",
				"	var o1 = Object.create();",
				"	TAJS_assert(false);",
				"}",
				"if(U){",
				"	var o2 = Object.create(null);",
				"	TAJS_dumpObject(o2);",
				"}",
				"if(U){",
				"	var o3 = Object.create(Array);",
				"	TAJS_dumpObject(o3);",
				"}",
				""
		);
		Misc.checkSystemOutput();
	}

	@Test
	public void unicodeCharAtTest(){
		Misc.init();
		Misc.runSource("",
				"var a = '\400'.charAt(1)",
				"TAJS_dumpValue('\400')",
				"TAJS_assert(a === '0')",
				"");
	}

	@Test(expected = AssertionError.class /* GitHub #223 */)
	public void unicodeCharAtTest_file(){
		Misc.init();
		String[] args = {"test/micro/unicodeCharAtTest.js"};
		Misc.run(args);
	}

	@Ignore // Picker & Maldonado
	@Test // Regression for GitHub issue #236
	public void absentPresentRecovery_regression() {
		Main.reset();
		Options.get().enableTest();
		Options.get().enableContextSensitiveHeap();
		Options.get().enableParameterSensitivity();
		Options.get().enableLoopUnrolling(1);

		Misc.init();
		String[] args = {"test/micro/absent-present.js"};
		Misc.run(args);
	}

	@Test
	public void testToString1() {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/toString1.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void testToString2() {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/toString2.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void testToString3() {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/toString3.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void testToString4() {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/toString4.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void testToString5() {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/toString5.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void arrayLengthNaN() {
		Misc.init();
		Misc.runSource("",
				"var arr = [];",
				"arr.length = Math.random()? 42: undefined;",
				"TAJS_assert(arr.length, 'isMaybeNumUInt');",
				"");
	}

	@Test(expected = AssertionError.class /* current implementation only throws a maybe-error */)
	public void invalidArrayLengthThrows() {
		Misc.init();
		Misc.runSource("",
				"var arr = [];",
				"arr.length = undefined;", // should throw a definite exception
				"TAJS_assert(false);",
				"");
	}

	@Test
	public void arraysort1() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/arraysort1.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void arraysort2() throws Exception {
		Misc.init();
		Misc.captureSystemOutput();
		String[] args = {"test/micro/arraysort2.js"};
		Misc.run(args);
		Misc.checkSystemOutput();
	}

	@Test
	public void typeofNative1() {
		Misc.init();
		Misc.runSource("",
				"TAJS_assert(typeof Number === 'function');",
				"");
	}

	@Test
	public void typeofNative2() {
		Misc.init();
		Misc.runSource("",
				"TAJS_assert(typeof Date === 'function');",
				"");
	}

	@Test
	public void emptyblock() {
		Misc.init();
		Options.get().enableFlowgraph();
		Misc.runSource("",
				"function f () {",
				"}",
				"function g () {",
				"}",
				"function h() {",
				"  while (true){",
				"    f[f] = function () { g(); };",
				"  }",
				"}",
				"h();",
				"");
	}

	@Test(expected = AnalysisLimitationException.class /* github #257 */)
	public void castbug() {
		Misc.init();
		Options.get().enableUnevalizer();
		Options.get().enableIncludeDom();
		Misc.runSource("setTimeout(Function,0);");
	}

	@Test
	public void indirecteval() {
		Misc.init();
//		Options.get().enableUnevalizer();
		Options.get().enableIncludeDom();
		Misc.runSource("document.addEventListener(\"DOMContentLoaded\", eval);");
	}


	@Test
	public void thisObjectForSetTimeoutAndSetInterval() {
		Options.get().enableIncludeDom();
		Misc.init();
		Misc.runSource(
				"setInterval(function(){TAJS_assert(window === this);}, 0);",
				"setTimeout(function(){TAJS_assert(window === this);}, 0);",
				"");
	}

	@Test
	public void constInitialization() {
		Misc.init();
		Misc.runSource(
				"const x = 42;",
				"TAJS_assert(x === 42);",
				"");
	}

	@Test(expected = AssertionError.class /* GitHub #182*/)
	public void constReassignment() {
		Misc.init();
		Misc.runSource(
				"const x = 42;",
				"x = 87;",
				"TAJS_assert(x === 42);",
				"");
	}

	@Test
	public void objectFreeze_return() {
		Misc.init();
		Misc.runSource(
				"var o = {};",
				"TAJS_assert(Object.freeze(o) === o);",
				"");
	}

	@Test(expected = AssertionError.class /* GitHub #249 */)
	public void objectFreeze_immutable() {
		Misc.init();
		Misc.runSource(
				"var o = {p: 'foo'};",
				"Object.freeze(o)",
				"o.p = 42;",
				"o.q = true;",
				"TAJS_assert(o.p === 'foo');",
				"TAJS_assert(o.q === undefined);",
				"");
	}

	@Test(expected = AssertionError.class /* GitHub #249 */)
	public void objectFreeze_strict() {
		Misc.init();
		Misc.runSource(
				"'use strict';",
				"var o = {};",
				"Object.freeze(o)",
				"o.p = 42;",
				"TAJS_assert(false);",
				"");
	}

	@Test
	public void objectKeys_typeError() {
		Misc.init();
		Misc.runSource(
				"Object.keys(undefined);",
				"TAJS_assert(false);"
		);
	}

	@Test
	public void objectKeys_sound() {
		Misc.init();
		Misc.runSource(
				"var o0 = {};",
				"TAJS_assert(Object.keys(o0).length === 0);",

				"var o1 = {p: 42};",
				"TAJS_assert(Object.keys(o1).length === 1);",
				"TAJS_assert(Object.keys(o1)[0] === 'p');",

				"var o2 = {p: 42, q: 87};",
				"TAJS_assert(Object.keys(o2).length === 2);",
				"TAJS_assert(Object.keys(o2)[0], 'isStrIdentifier');",
				"TAJS_assert(Object.keys(o2)[1], 'isStrIdentifier');",

				"var oMaybe1 = {};",
				"if(Math.random()){oMaybe1.p = 42;}",
				"TAJS_assert(Object.keys(oMaybe1).length, 'isMaybeNumUInt');",
				"TAJS_assert(Object.keys(oMaybe1)[0], 'isMaybeSingleStr||isMaybeUndef');"
		);
	}

	@Test
	public void objectKeys_unsound() {
		Options.get().enableUnsound();
		Misc.init();
		Misc.runSource(
				"var o0 = {};",
				"TAJS_assert(Object.keys(o0).length === 0);",

				"var o1 = {p: 42};",
				"TAJS_assert(Object.keys(o1).length === 1);",
				"TAJS_assert(Object.keys(o1)[0] === 'p');",

				"var o2 = {p: 42, q: 87};",
				"TAJS_assert(Object.keys(o2).length === 2);",
				"TAJS_assert(Object.keys(o2)[0] === 'p');",
				"TAJS_assert(Object.keys(o2)[1] === 'q');",

				"var oMaybe1 = {};",
				"if(Math.random()){oMaybe1.p = 42;}",
				"TAJS_assert(Object.keys(oMaybe1).length, 'isMaybeNumUInt');",
				"TAJS_assert(Object.keys(oMaybe1)[0], 'isMaybeSingleStr||isMaybeUndef');"
		);
	}

	@Test
	public void functionToString_unsound() {
		Options.get().enableUnsound();
		Misc.init();
		Misc.runSource(
				// plain content
				"TAJS_assert((function foo(bar){baz;}).toString() === 'function foo(bar){baz;}');",
				// whitespace
				"TAJS_assert((function foo ( bar ){ baz ; }).toString() === 'function foo ( bar ){ baz ; }');",
				// removed parts (nb: nashorn-semantics, google-chrome removes some of the comments)
				"TAJS_assert((function /**/foo/**/(/**/bar/**/)/**/{/**/baz;/**/}).toString() === 'function /**/foo/**/(/**/bar/**/)/**/{/**/baz;/**/}');",
				// newlines
				"TAJS_assert((function foo(bar){\nbaz;\n}).toString() === 'function foo(bar){\\nbaz;\\n}');",
				// identical functions
				"var f = !!Math.random()? function f(){}: function f(){};",
				"TAJS_assert(f.toString() === 'function f(){}');",
				// different functions
				"var fg = !!Math.random()? function f(){}: function g(){};",
				"TAJS_assert(fg.toString(), 'isMaybeStrOther');",
				// natives (nb: nashorn-semantics, google-chrome has space before the parameter list)
				"TAJS_assert(toString.toString() === 'function toString() { [native code] }');",
				"TAJS_assert(Array.prototype.push.toString() === 'function push() { [native code] }');"
		);
	}

	@Test
	public void functionToString_sound() {
		Misc.init();
		Misc.runSource(
				"TAJS_assert(toString.toString(), 'isMaybeAnyStr');",
				"TAJS_assert((function f(){}).toString(), 'isMaybeAnyStr');"
		);
	}

	@Test
	public void functionToString_prototypejsExample() {
		Options.get().enableUnsound();
		Misc.init();
		Misc.runSource(
				// prototype.js depends on Function.prototype.toString for extracting argument names
				"function f(a, b, c){foo;}",
				"var args = f.toString().match(/^[\\s\\(]*function[^(]*\\((.*?)\\)/)[1].split(',');",
				"TAJS_assert(args.length === 3);",
				"TAJS_assert(args[0] === 'a');",
				"TAJS_assert(args[2] === ' c');" /* this is before a call to strip! */
		);
	}

	@Test
	public void testNoImplicitGlobalVarDeclarations() {
		Misc.init();
		Options.get().enableNoImplicitGlobalVarDeclarations();
		Misc.runSource(
				"var v1 = 42;",
				"TAJS_assert(v1 === 42);",
				"v2 = 42;",
				"TAJS_assert(typeof v2 === 'undefined');"
		);
	}

	@Test
	public void recursiveSetTimeoutWithNulls() {
		Misc.init();
		Options.get().enableIncludeDom();
		Misc.captureSystemOutput();
		Misc.runSourceWithNamedFile("recursiveSetTimeoutWithNulls.js",
				"function r(f){",
				"	setTimeout(f, 0);",
				"}",
				"r(function f1(){r(function f2(){r(function f3(){r(null)})})});"
		);
		Misc.checkSystemOutput();
	}
}
