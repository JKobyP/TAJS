package edu.oakland.stringabs;
import dk.brics.automaton.*;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.BasicAutomata;
import dk.brics.automaton.BasicOperations;
import dk.brics.automaton.RegExp;
import dk.brics.automaton.SpecialOperations;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Set;

/**
 * Created by koby on 6/9/2016.
 */
public class AbstractString implements AbstractOperations {
    private Automaton dfa;
    private static AbstractString anyString;
    private static AbstractString uIntString;
    private static AbstractString otherNumString;
    private static AbstractString anyNumberString;
    private static AbstractString empty;
    private static AbstractString identifierString;
    private static AbstractString identifierPartsString;
    private static AbstractString stringOther;

    public static AbstractString newEmptyAbstractString(){
        if (empty == null) {
            empty = new AbstractString(BasicAutomata.makeEmpty());
        }
        return empty;
    }

    public static AbstractString anyString(){
        if (anyString == null) {
            anyString = new AbstractString(BasicAutomata.makeAnyString());
        }
        return anyString;
    }
// ---------------------------------------------------------------
// Test these
// ---------------------------------------------------------------
    public static AbstractString uIntString(){
        if (uIntString == null) {
//            String uintsize = String.valueOf((long)Integer.MAX_VALUE * 2);
            uIntString = new AbstractString(BasicAutomata.makeMinInteger("0"));
//                    .intersection(BasicAutomata.makeMaxInteger(uintsize)));
        }
        return uIntString;
    }

    public static AbstractString otherNumString() {
        if(otherNumString == null) {
            otherNumString = getAnyNumberString().intersect(uIntString().getComplement());
        }
        return otherNumString;
    }

    public static AbstractString getAnyNumberString() {
        if(anyNumberString == null) {
            anyNumberString = new AbstractString(new RegExp("\\-?(([0-9]+(\\.[0-9]*)?|\\.[0-9]+)([eE][-+]?[0-9]+)?|Infinity)|NaN").toAutomaton(true));
        }
        return anyNumberString;
    }

    public static AbstractString getAnyString() {
        return anyString;
    }

    // FIXME: Incredibly expensive
    public static AbstractString getIdentifierString() {
        final String IDENTIFIERHEAD = "A-Za-zªµºÀ-ÖØ-öø-ˁˆ-ˑ ˠ-ˤˬˮͰ-ʹͶͷͺ-ͽ\u037FΆΈ-ΊΌΎ-Ρ Σ-ϵϷ-ҁҊ-\u052FԱ-Ֆՙա-ևא-תװ-ײ ؠ-يٮٯٱ-ۓەۥۦۮۯۺ-ۼۿܐܒ-ܯ ݍ-ޥޱߊ-ߪߴߵߺࠀ-ࠕࠚࠤࠨࡀ-ࡘࢠ-\u08B4 ऄ-हऽॐक़-ॡॱ-\u0980অ-ঌএঐও-নপ-র লশ-হঽৎড়ঢ়য়-ৡৰৱਅ-ਊਏਐਓ-ਨ ਪ-ਰਲਲ਼ਵਸ਼ਸਹਖ਼-ੜਫ਼ੲ-ੴઅ-ઍએ-ઑ ઓ-નપ-રલળવ-હઽૐૠૡ\u0AF9ଅ-ଌଏଐ ଓ-ନପ-ରଲଳଵ-ହଽଡ଼ଢ଼ୟ-ୡୱஃஅ-ஊ எ-ஐஒ-கஙசஜஞடணதந-பம-ஹௐఅ-ఌ ఎ-ఐఒ-నప-హఽౘ-\u0C5Aౠౡಅ-ಌಎ-ಐ ಒ-ನಪ-ಳವ-ಹಽೞೠೡೱೲഅ-ഌഎ-ഐ ഒ-ഺഽൎ\u0D5F-ൡൺ-ൿඅ-ඖක-නඳ-රල ව-ෆก-ะาำเ-ๆກຂຄງຈຊຍດ-ທ ນ-ຟມ-ຣລວສຫອ-ະາຳຽເ-ໄໆໜ-ໟ ༀཀ-ཇཉ-ཬྈ-ྌက-ဪဿၐ-ၕၚ-ၝၡ ၥၦၮ-ၰၵ-ႁႎႠ-ჅჇჍა-ჺჼ-ቈቊ-ቍ ቐ-ቖቘቚ-ቝበ-ኈኊ-ኍነ-ኰኲ-ኵኸ-ኾ ዀዂ-ዅወ-ዖዘ-ጐጒ-ጕጘ-ፚᎀ-ᎏᎠ-\u13F5 \u13F8-\u13FDᐁ-ᙬᙯ-ᙿᚁ-ᚚᚠ-ᛪᛮ-\u16F8ᜀ-ᜌ ᜎ-ᜑᜠ-ᜱᝀ-ᝑᝠ-ᝬᝮ-ᝰក-ឳៗៜᠠ-ᡷ ᢀ-ᢨᢪᢰ-ᣵᤀ-\u191Eᥐ-ᥭᥰ-ᥴᦀ-ᦫᦰ-ᧉ ᨀ-ᨖᨠ-ᩔᪧᬅ-ᬳᭅ-ᭋᮃ-ᮠᮮᮯᮺ-ᯥ ᰀ-ᰣᱍ-ᱏᱚ-ᱽᳩ-ᳬᳮ-ᳱᳵᳶᴀ-ᶿḀ-ἕ Ἐ-Ἕἠ-ὅὈ-Ὅὐ-ὗὙὛὝὟ-ώᾀ-ᾴ ᾶ-ᾼιῂ-ῄῆ-ῌῐ-ΐῖ-Ίῠ-Ῥῲ-ῴ ῶ-ῼⁱⁿₐ-ₜℂℇℊ-ℓℕℙ-ℝℤΩℨK-ℭ ℯ-ℹℼ-ℿⅅ-ⅉⅎⅠ-ↈⰀ-Ⱞⰰ-ⱞⱠ-ⳤ Ⳬ-ⳮⳲⳳⴀ-ⴥⴧⴭⴰ-ⵧⵯⶀ-ⶖⶠ-ⶦⶨ-ⶮ ⶰ-ⶶⶸ-ⶾⷀ-ⷆⷈ-ⷎⷐ-ⷖⷘ-ⷞⸯ々-〇 〡-〩〱-〵〸-〼ぁ-ゖゝ-ゟァ-ヺー-ヿ ㄅ-ㄭㄱ-ㆎㆠ-ㆺㇰ-ㇿ㐀-䶵一-\u9FD5ꀀ-ꒌ ꓐ-ꓽꔀ-ꘌꘐ-ꘟꘪꘫꙀ-ꙮꙿ-\uA69Dꚠ-ꛯꜗ-ꜟ Ꜣ-ꞈꞋ-\uA7AD\uA7B0-\uA7B7\uA7F7-ꠁꠃ-ꠅꠇ-ꠊꠌ-ꠢ ꡀ-ꡳꢂ-ꢳꣲ-ꣷꣻ\uA8FDꤊ-ꤥꤰ-ꥆꥠ-ꥼꦄ-ꦲ ꧏ\uA9E0-\uA9E4\uA9E6-\uA9EF\uA9FA-\uA9FEꨀ-ꨨꩀ-ꩂꩄ-ꩋꩠ-ꩶ ꩺ\uAA7E-ꪯꪱꪵꪶꪹ-ꪽꫀꫂꫛ-ꫝꫠ-ꫪꫲ-ꫴ ꬁ-ꬆꬉ-ꬎꬑ-ꬖꬠ-ꬦꬨ-ꬮ\uAB30-\uAB5A\uAB5C-\uAB65 \uAB70-ꯢ가-힣ힰ-ퟆퟋ-ퟻ豈-舘並-龎ﬀ-ﬆ ﬓ-ﬗיִײַ-ﬨשׁ-זּטּ-לּמּנּסּףּפּצּ-ﮱ ﯓ-ﴽﵐ-ﶏﶒ-ﷇﷰ-ﷻﹰ-ﹴﹶ-ﻼＡ-Ｚ ａ-ｚｦ-ﾾￂ-ￇￊ-ￏￒ-ￗￚ-ￜ\uD800\uDC00-\uD800\uDC0B \uD800\uDC0D-\uD800\uDC26\uD800\uDC28-\uD800\uDC3A\uD800\uDC3C\uD800\uDC3D\uD800\uDC3F-\uD800\uDC4D\uD800\uDC50-\uD800\uDC5D\uD800\uDC80-\uD800\uDCFA\uD800\uDD40-\uD800\uDD74\uD800\uDE80-\uD800\uDE9C \uD800\uDEA0-\uD800\uDED0\uD800\uDF00-\uD800\uDF1F\uD800\uDF30-\uD800\uDF4A\uD800\uDF50-\uD800\uDF75\uD800\uDF80-\uD800\uDF9D\uD800\uDFA0-\uD800\uDFC3\uD800\uDFC8-\uD800\uDFCF \uD800\uDFD1-\uD800\uDFD5\uD801\uDC00-\uD801\uDC9D\uD801\uDD00-\uD801\uDD27\uD801\uDD30-\uD801\uDD63\uD801\uDE00-\uD801\uDF36\uD801\uDF40-\uD801\uDF55\uD801\uDF60-\uD801\uDF67 \uD802\uDC00-\uD802\uDC05\uD802\uDC08\uD802\uDC0A-\uD802\uDC35\uD802\uDC37\uD802\uDC38\uD802\uDC3C\uD802\uDC3F-\uD802\uDC55\uD802\uDC60-\uD802\uDC76\uD802\uDC80-\uD802\uDC9E\uD802\uDCE0-\uD802\uDCF2 \uD802\uDCF4\uD802\uDCF5\uD802\uDD00-\uD802\uDD15\uD802\uDD20-\uD802\uDD39\uD802\uDD80-\uD802\uDDB7\uD802\uDDBE\uD802\uDDBF\uD802\uDE00\uD802\uDE10-\uD802\uDE13\uD802\uDE15-\uD802\uDE17\uD802\uDE19-\uD802\uDE33 \uD802\uDE60-\uD802\uDE7C\uD802\uDE80-\uD802\uDE9C\uD802\uDEC0-\uD802\uDEC7\uD802\uDEC9-\uD802\uDEE4\uD802\uDF00-\uD802\uDF35\uD802\uDF40-\uD802\uDF55\uD802\uDF60-\uD802\uDF72 \uD802\uDF80-\uD802\uDF91\uD803\uDC00-\uD803\uDC48\uD803\uDC80-\uD803\uDCB2\uD803\uDCC0-\uD803\uDCF2\uD804\uDC03-\uD804\uDC37\uD804\uDC83-\uD804\uDCAF\uD804\uDCD0-\uD804\uDCE8 \uD804\uDD03-\uD804\uDD26\uD804\uDD50-\uD804\uDD72\uD804\uDD76\uD804\uDD83-\uD804\uDDB2\uD804\uDDC1-\uD804\uDDC4\uD804\uDDDA\uD804\uDDDC\uD804\uDE00-\uD804\uDE11\uD804\uDE13-\uD804\uDE2B \uD804\uDE80-\uD804\uDE86\uD804\uDE88\uD804\uDE8A-\uD804\uDE8D\uD804\uDE8F-\uD804\uDE9D\uD804\uDE9F-\uD804\uDEA8\uD804\uDEB0-\uD804\uDEDE\uD804\uDF05-\uD804\uDF0C\uD804\uDF0F\uD804\uDF10 \uD804\uDF13-\uD804\uDF28\uD804\uDF2A-\uD804\uDF30\uD804\uDF32\uD804\uDF33\uD804\uDF35-\uD804\uDF39\uD804\uDF3D\uD804\uDF50\uD804\uDF5D-\uD804\uDF61\uD805\uDC80-\uD805\uDCAF\uD805\uDCC4\uD805\uDCC5 \uD805\uDCC7\uD805\uDD80-\uD805\uDDAE\uD805\uDDD8-\uD805\uDDDB\uD805\uDE00-\uD805\uDE2F\uD805\uDE44\uD805\uDE80-\uD805\uDEAA\uD805\uDF00-\uD805\uDF19\uD806\uDCA0-\uD806\uDCDF\uD806\uDCFF \uD806\uDEC0-\uD806\uDEF8\uD808\uDC00-\uD808\uDF99\uD809\uDC00-\uD809\uDC6E\uD809\uDC80-\uD809\uDD43\uD80C\uDC00-\uD80D\uDC2E\uD811\uDC00-\uD811\uDE46\uD81A\uDC00-\uD81A\uDE38 \uD81A\uDE40-\uD81A\uDE5E\uD81A\uDED0-\uD81A\uDEED\uD81A\uDF00-\uD81A\uDF2F\uD81A\uDF40-\uD81A\uDF43\uD81A\uDF63-\uD81A\uDF77\uD81A\uDF7D-\uD81A\uDF8F\uD81B\uDF00-\uD81B\uDF44 \uD81B\uDF50\uD81B\uDF93-\uD81B\uDF9F\uD82C\uDC00\uD82C\uDC01\uD82F\uDC00-\uD82F\uDC6A\uD82F\uDC70-\uD82F\uDC7C\uD82F\uDC80-\uD82F\uDC88\uD82F\uDC90-\uD82F\uDC99\uD835\uDC00-\uD835\uDC54 \uD835\uDC56-\uD835\uDC9C\uD835\uDC9E\uD835\uDC9F\uD835\uDCA2\uD835\uDCA5\uD835\uDCA6\uD835\uDCA9-\uD835\uDCAC\uD835\uDCAE-\uD835\uDCB9\uD835\uDCBB\uD835\uDCBD-\uD835\uDCC3\uD835\uDCC5-\uD835\uDD05 \uD835\uDD07-\uD835\uDD0A\uD835\uDD0D-\uD835\uDD14\uD835\uDD16-\uD835\uDD1C\uD835\uDD1E-\uD835\uDD39\uD835\uDD3B-\uD835\uDD3E\uD835\uDD40-\uD835\uDD44\uD835\uDD46\uD835\uDD4A-\uD835\uDD50 \uD835\uDD52-\uD835\uDEA5\uD835\uDEA8-\uD835\uDEC0\uD835\uDEC2-\uD835\uDEDA\uD835\uDEDC-\uD835\uDEFA\uD835\uDEFC-\uD835\uDF14\uD835\uDF16-\uD835\uDF34\uD835\uDF36-\uD835\uDF4E \uD835\uDF50-\uD835\uDF6E\uD835\uDF70-\uD835\uDF88\uD835\uDF8A-\uD835\uDFA8\uD835\uDFAA-\uD835\uDFC2\uD835\uDFC4-\uD835\uDFCB\uD83A\uDC00-\uD83A\uDCC4\uD83B\uDE00-\uD83B\uDE03 \uD83B\uDE05-\uD83B\uDE1F\uD83B\uDE21\uD83B\uDE22\uD83B\uDE24\uD83B\uDE27\uD83B\uDE29-\uD83B\uDE32\uD83B\uDE34-\uD83B\uDE37\uD83B\uDE39\uD83B\uDE3B\uD83B\uDE42\uD83B\uDE47\uD83B\uDE49\uD83B\uDE4B\uD83B\uDE4D-\uD83B\uDE4F \uD83B\uDE51\uD83B\uDE52\uD83B\uDE54\uD83B\uDE57\uD83B\uDE59\uD83B\uDE5B\uD83B\uDE5D\uD83B\uDE5F\uD83B\uDE61\uD83B\uDE62\uD83B\uDE64\uD83B\uDE67-\uD83B\uDE6A\uD83B\uDE6C-\uD83B\uDE72\uD83B\uDE74-\uD83B\uDE77\uD83B\uDE79-\uD83B\uDE7C \uD83B\uDE7E\uD83B\uDE80-\uD83B\uDE89\uD83B\uDE8B-\uD83B\uDE9B\uD83B\uDEA1-\uD83B\uDEA3\uD83B\uDEA5-\uD83B\uDEA9\uD83B\uDEAB-\uD83B\uDEBB\uD840\uDC00-\uD869\uDED6\uD869\uDF00-\uD86D\uDF34 \uD86D\uDF40-\uD86E\uDC1D\uD86E\uDC20-\uD873\uDEA1\uD87E\uDC00-\uD87E\uDE1D";
        // IDENTIFIERPARTPART does not include supplementary characters that reach above U+FFFF, but those are used very seldom in practice
        final String IDENTIFIERPARTPART = "0-9_\u0300-\u036F\u0483-\u0487\u0591-\u05BD\u05BF\u05C1\u05C2\u05C4\u05C5\u05C7\u0610-\u061A\u064B-\u0669\u0670\u06D6-\u06DC\u06DF-\u06E4\u06E7\u06E8\u06EA-\u06ED\u06F0-\u06F9\u0711\u0730-\u074A\u07A6-\u07B0\u07C0-\u07C9\u07EB-\u07F3\u0816-\u0819\u081B-\u0823\u0825-\u0827\u0829-\u082D\u0859-\u085B\u08E3-\u0903\u093A-\u093C\u093E-\u094F\u0951-\u0957\u0962\u0963\u0966-\u096F\u0981-\u0983\u09BC\u09BE-\u09C4\u09C7\u09C8\u09CB-\u09CD\u09D7\u09E2\u09E3\u09E6-\u09EF\u0A01-\u0A03\u0A3C\u0A3E-\u0A42\u0A47\u0A48\u0A4B-\u0A4D\u0A51\u0A66-\u0A71\u0A75\u0A81-\u0A83\u0ABC\u0ABE-\u0AC5\u0AC7-\u0AC9\u0ACB-\u0ACD\u0AE2\u0AE3\u0AE6-\u0AEF\u0B01-\u0B03\u0B3C\u0B3E-\u0B44\u0B47\u0B48\u0B4B-\u0B4D\u0B56\u0B57\u0B62\u0B63\u0B66-\u0B6F\u0B82\u0BBE-\u0BC2\u0BC6-\u0BC8\u0BCA-\u0BCD\u0BD7\u0BE6-\u0BEF\u0C00-\u0C03\u0C3E-\u0C44\u0C46-\u0C48\u0C4A-\u0C4D\u0C55\u0C56\u0C62\u0C63\u0C66-\u0C6F\u0C81-\u0C83\u0CBC\u0CBE-\u0CC4\u0CC6-\u0CC8\u0CCA-\u0CCD\u0CD5\u0CD6\u0CE2\u0CE3\u0CE6-\u0CEF\u0D01-\u0D03\u0D3E-\u0D44\u0D46-\u0D48\u0D4A-\u0D4D\u0D57\u0D62\u0D63\u0D66-\u0D6F\u0D82\u0D83\u0DCA\u0DCF-\u0DD4\u0DD6\u0DD8-\u0DDF\u0DE6-\u0DEF\u0DF2\u0DF3\u0E31\u0E34-\u0E3A\u0E47-\u0E4E\u0E50-\u0E59\u0EB1\u0EB4-\u0EB9\u0EBB\u0EBC\u0EC8-\u0ECD\u0ED0-\u0ED9\u0F18\u0F19\u0F20-\u0F29\u0F35\u0F37\u0F39\u0F3E\u0F3F\u0F71-\u0F84\u0F86\u0F87\u0F8D-\u0F97\u0F99-\u0FBC\u0FC6\u102B-\u103E\u1040-\u1049\u1056-\u1059\u105E-\u1060\u1062-\u1064\u1067-\u106D\u1071-\u1074\u1082-\u108D\u108F-\u109D\u135D-\u135F\u1712-\u1714\u1732-\u1734\u1752\u1753\u1772\u1773\u17B4-\u17D3\u17DD\u17E0-\u17E9\u180B-\u180D\u1810-\u1819\u18A9\u1920-\u192B\u1930-\u193B\u1946-\u194F\u19D0-\u19D9\u1A17-\u1A1B\u1A55-\u1A5E\u1A60-\u1A7C\u1A7F-\u1A89\u1A90-\u1A99\u1AB0-\u1ABD\u1B00-\u1B04\u1B34-\u1B44\u1B50-\u1B59\u1B6B-\u1B73\u1B80-\u1B82\u1BA1-\u1BAD\u1BB0-\u1BB9\u1BE6-\u1BF3\u1C24-\u1C37\u1C40-\u1C49\u1C50-\u1C59\u1CD0-\u1CD2\u1CD4-\u1CE8\u1CED\u1CF2-\u1CF4\u1CF8\u1CF9\u1DC0-\u1DF5\u1DFC-\u1DFF\u203F\u2040\u2054\u20D0-\u20DC\u20E1\u20E5-\u20F0\u2CEF-\u2CF1\u2D7F\u2DE0-\u2DFF\u302A-\u302F\u3099\u309A\uA620-\uA629\uA66F\uA674-\uA67D\uA69E\uA69F\uA6F0\uA6F1\uA802\uA806\uA80B\uA823-\uA827\uA880\uA881\uA8B4-\uA8C4\uA8D0-\uA8D9\uA8E0-\uA8F1\uA900-\uA909\uA926-\uA92D\uA947-\uA953\uA980-\uA983\uA9B3-\uA9C0\uA9D0-\uA9D9\uA9E5\uA9F0-\uA9F9\uAA29-\uAA36\uAA43\uAA4C\uAA4D\uAA50-\uAA59\uAA7B-\uAA7D\uAAB0\uAAB2-\uAAB4\uAAB7\uAAB8\uAABE\uAABF\uAAC1\uAAEB-\uAAEF\uAAF5\uAAF6\uABE3-\uABEA\uABEC\uABED\uABF0-\uABF9\uFB1E\uFE00-\uFE0F\uFE20-\uFE2F\uFE33\uFE34\uFE4D-\uFE4F\uFF10-\uFF19\uFF3F\u000101FD";
        if(identifierString == null) {
            identifierString = new AbstractString(new RegExp("["+IDENTIFIERHEAD + "$_]" +
                    "[" + IDENTIFIERHEAD + IDENTIFIERPARTPART+ "]*").toAutomaton(true));
        }
        return identifierString;
    }

    public static AbstractString getIdentifierPartsString() {
        final String IDENTIFIERHEAD = "A-Za-zªµºÀ-ÖØ-öø-ˁˆ-ˑ ˠ-ˤˬˮͰ-ʹͶͷͺ-ͽ\u037FΆΈ-ΊΌΎ-Ρ Σ-ϵϷ-ҁҊ-\u052FԱ-Ֆՙա-ևא-תװ-ײ ؠ-يٮٯٱ-ۓەۥۦۮۯۺ-ۼۿܐܒ-ܯ ݍ-ޥޱߊ-ߪߴߵߺࠀ-ࠕࠚࠤࠨࡀ-ࡘࢠ-\u08B4 ऄ-हऽॐक़-ॡॱ-\u0980অ-ঌএঐও-নপ-র লশ-হঽৎড়ঢ়য়-ৡৰৱਅ-ਊਏਐਓ-ਨ ਪ-ਰਲਲ਼ਵਸ਼ਸਹਖ਼-ੜਫ਼ੲ-ੴઅ-ઍએ-ઑ ઓ-નપ-રલળવ-હઽૐૠૡ\u0AF9ଅ-ଌଏଐ ଓ-ନପ-ରଲଳଵ-ହଽଡ଼ଢ଼ୟ-ୡୱஃஅ-ஊ எ-ஐஒ-கஙசஜஞடணதந-பம-ஹௐఅ-ఌ ఎ-ఐఒ-నప-హఽౘ-\u0C5Aౠౡಅ-ಌಎ-ಐ ಒ-ನಪ-ಳವ-ಹಽೞೠೡೱೲഅ-ഌഎ-ഐ ഒ-ഺഽൎ\u0D5F-ൡൺ-ൿඅ-ඖක-නඳ-රල ව-ෆก-ะาำเ-ๆກຂຄງຈຊຍດ-ທ ນ-ຟມ-ຣລວສຫອ-ະາຳຽເ-ໄໆໜ-ໟ ༀཀ-ཇཉ-ཬྈ-ྌက-ဪဿၐ-ၕၚ-ၝၡ ၥၦၮ-ၰၵ-ႁႎႠ-ჅჇჍა-ჺჼ-ቈቊ-ቍ ቐ-ቖቘቚ-ቝበ-ኈኊ-ኍነ-ኰኲ-ኵኸ-ኾ ዀዂ-ዅወ-ዖዘ-ጐጒ-ጕጘ-ፚᎀ-ᎏᎠ-\u13F5 \u13F8-\u13FDᐁ-ᙬᙯ-ᙿᚁ-ᚚᚠ-ᛪᛮ-\u16F8ᜀ-ᜌ ᜎ-ᜑᜠ-ᜱᝀ-ᝑᝠ-ᝬᝮ-ᝰក-ឳៗៜᠠ-ᡷ ᢀ-ᢨᢪᢰ-ᣵᤀ-\u191Eᥐ-ᥭᥰ-ᥴᦀ-ᦫᦰ-ᧉ ᨀ-ᨖᨠ-ᩔᪧᬅ-ᬳᭅ-ᭋᮃ-ᮠᮮᮯᮺ-ᯥ ᰀ-ᰣᱍ-ᱏᱚ-ᱽᳩ-ᳬᳮ-ᳱᳵᳶᴀ-ᶿḀ-ἕ Ἐ-Ἕἠ-ὅὈ-Ὅὐ-ὗὙὛὝὟ-ώᾀ-ᾴ ᾶ-ᾼιῂ-ῄῆ-ῌῐ-ΐῖ-Ίῠ-Ῥῲ-ῴ ῶ-ῼⁱⁿₐ-ₜℂℇℊ-ℓℕℙ-ℝℤΩℨK-ℭ ℯ-ℹℼ-ℿⅅ-ⅉⅎⅠ-ↈⰀ-Ⱞⰰ-ⱞⱠ-ⳤ Ⳬ-ⳮⳲⳳⴀ-ⴥⴧⴭⴰ-ⵧⵯⶀ-ⶖⶠ-ⶦⶨ-ⶮ ⶰ-ⶶⶸ-ⶾⷀ-ⷆⷈ-ⷎⷐ-ⷖⷘ-ⷞⸯ々-〇 〡-〩〱-〵〸-〼ぁ-ゖゝ-ゟァ-ヺー-ヿ ㄅ-ㄭㄱ-ㆎㆠ-ㆺㇰ-ㇿ㐀-䶵一-\u9FD5ꀀ-ꒌ ꓐ-ꓽꔀ-ꘌꘐ-ꘟꘪꘫꙀ-ꙮꙿ-\uA69Dꚠ-ꛯꜗ-ꜟ Ꜣ-ꞈꞋ-\uA7AD\uA7B0-\uA7B7\uA7F7-ꠁꠃ-ꠅꠇ-ꠊꠌ-ꠢ ꡀ-ꡳꢂ-ꢳꣲ-ꣷꣻ\uA8FDꤊ-ꤥꤰ-ꥆꥠ-ꥼꦄ-ꦲ ꧏ\uA9E0-\uA9E4\uA9E6-\uA9EF\uA9FA-\uA9FEꨀ-ꨨꩀ-ꩂꩄ-ꩋꩠ-ꩶ ꩺ\uAA7E-ꪯꪱꪵꪶꪹ-ꪽꫀꫂꫛ-ꫝꫠ-ꫪꫲ-ꫴ ꬁ-ꬆꬉ-ꬎꬑ-ꬖꬠ-ꬦꬨ-ꬮ\uAB30-\uAB5A\uAB5C-\uAB65 \uAB70-ꯢ가-힣ힰ-ퟆퟋ-ퟻ豈-舘並-龎ﬀ-ﬆ ﬓ-ﬗיִײַ-ﬨשׁ-זּטּ-לּמּנּסּףּפּצּ-ﮱ ﯓ-ﴽﵐ-ﶏﶒ-ﷇﷰ-ﷻﹰ-ﹴﹶ-ﻼＡ-Ｚ ａ-ｚｦ-ﾾￂ-ￇￊ-ￏￒ-ￗￚ-ￜ\uD800\uDC00-\uD800\uDC0B \uD800\uDC0D-\uD800\uDC26\uD800\uDC28-\uD800\uDC3A\uD800\uDC3C\uD800\uDC3D\uD800\uDC3F-\uD800\uDC4D\uD800\uDC50-\uD800\uDC5D\uD800\uDC80-\uD800\uDCFA\uD800\uDD40-\uD800\uDD74\uD800\uDE80-\uD800\uDE9C \uD800\uDEA0-\uD800\uDED0\uD800\uDF00-\uD800\uDF1F\uD800\uDF30-\uD800\uDF4A\uD800\uDF50-\uD800\uDF75\uD800\uDF80-\uD800\uDF9D\uD800\uDFA0-\uD800\uDFC3\uD800\uDFC8-\uD800\uDFCF \uD800\uDFD1-\uD800\uDFD5\uD801\uDC00-\uD801\uDC9D\uD801\uDD00-\uD801\uDD27\uD801\uDD30-\uD801\uDD63\uD801\uDE00-\uD801\uDF36\uD801\uDF40-\uD801\uDF55\uD801\uDF60-\uD801\uDF67 \uD802\uDC00-\uD802\uDC05\uD802\uDC08\uD802\uDC0A-\uD802\uDC35\uD802\uDC37\uD802\uDC38\uD802\uDC3C\uD802\uDC3F-\uD802\uDC55\uD802\uDC60-\uD802\uDC76\uD802\uDC80-\uD802\uDC9E\uD802\uDCE0-\uD802\uDCF2 \uD802\uDCF4\uD802\uDCF5\uD802\uDD00-\uD802\uDD15\uD802\uDD20-\uD802\uDD39\uD802\uDD80-\uD802\uDDB7\uD802\uDDBE\uD802\uDDBF\uD802\uDE00\uD802\uDE10-\uD802\uDE13\uD802\uDE15-\uD802\uDE17\uD802\uDE19-\uD802\uDE33 \uD802\uDE60-\uD802\uDE7C\uD802\uDE80-\uD802\uDE9C\uD802\uDEC0-\uD802\uDEC7\uD802\uDEC9-\uD802\uDEE4\uD802\uDF00-\uD802\uDF35\uD802\uDF40-\uD802\uDF55\uD802\uDF60-\uD802\uDF72 \uD802\uDF80-\uD802\uDF91\uD803\uDC00-\uD803\uDC48\uD803\uDC80-\uD803\uDCB2\uD803\uDCC0-\uD803\uDCF2\uD804\uDC03-\uD804\uDC37\uD804\uDC83-\uD804\uDCAF\uD804\uDCD0-\uD804\uDCE8 \uD804\uDD03-\uD804\uDD26\uD804\uDD50-\uD804\uDD72\uD804\uDD76\uD804\uDD83-\uD804\uDDB2\uD804\uDDC1-\uD804\uDDC4\uD804\uDDDA\uD804\uDDDC\uD804\uDE00-\uD804\uDE11\uD804\uDE13-\uD804\uDE2B \uD804\uDE80-\uD804\uDE86\uD804\uDE88\uD804\uDE8A-\uD804\uDE8D\uD804\uDE8F-\uD804\uDE9D\uD804\uDE9F-\uD804\uDEA8\uD804\uDEB0-\uD804\uDEDE\uD804\uDF05-\uD804\uDF0C\uD804\uDF0F\uD804\uDF10 \uD804\uDF13-\uD804\uDF28\uD804\uDF2A-\uD804\uDF30\uD804\uDF32\uD804\uDF33\uD804\uDF35-\uD804\uDF39\uD804\uDF3D\uD804\uDF50\uD804\uDF5D-\uD804\uDF61\uD805\uDC80-\uD805\uDCAF\uD805\uDCC4\uD805\uDCC5 \uD805\uDCC7\uD805\uDD80-\uD805\uDDAE\uD805\uDDD8-\uD805\uDDDB\uD805\uDE00-\uD805\uDE2F\uD805\uDE44\uD805\uDE80-\uD805\uDEAA\uD805\uDF00-\uD805\uDF19\uD806\uDCA0-\uD806\uDCDF\uD806\uDCFF \uD806\uDEC0-\uD806\uDEF8\uD808\uDC00-\uD808\uDF99\uD809\uDC00-\uD809\uDC6E\uD809\uDC80-\uD809\uDD43\uD80C\uDC00-\uD80D\uDC2E\uD811\uDC00-\uD811\uDE46\uD81A\uDC00-\uD81A\uDE38 \uD81A\uDE40-\uD81A\uDE5E\uD81A\uDED0-\uD81A\uDEED\uD81A\uDF00-\uD81A\uDF2F\uD81A\uDF40-\uD81A\uDF43\uD81A\uDF63-\uD81A\uDF77\uD81A\uDF7D-\uD81A\uDF8F\uD81B\uDF00-\uD81B\uDF44 \uD81B\uDF50\uD81B\uDF93-\uD81B\uDF9F\uD82C\uDC00\uD82C\uDC01\uD82F\uDC00-\uD82F\uDC6A\uD82F\uDC70-\uD82F\uDC7C\uD82F\uDC80-\uD82F\uDC88\uD82F\uDC90-\uD82F\uDC99\uD835\uDC00-\uD835\uDC54 \uD835\uDC56-\uD835\uDC9C\uD835\uDC9E\uD835\uDC9F\uD835\uDCA2\uD835\uDCA5\uD835\uDCA6\uD835\uDCA9-\uD835\uDCAC\uD835\uDCAE-\uD835\uDCB9\uD835\uDCBB\uD835\uDCBD-\uD835\uDCC3\uD835\uDCC5-\uD835\uDD05 \uD835\uDD07-\uD835\uDD0A\uD835\uDD0D-\uD835\uDD14\uD835\uDD16-\uD835\uDD1C\uD835\uDD1E-\uD835\uDD39\uD835\uDD3B-\uD835\uDD3E\uD835\uDD40-\uD835\uDD44\uD835\uDD46\uD835\uDD4A-\uD835\uDD50 \uD835\uDD52-\uD835\uDEA5\uD835\uDEA8-\uD835\uDEC0\uD835\uDEC2-\uD835\uDEDA\uD835\uDEDC-\uD835\uDEFA\uD835\uDEFC-\uD835\uDF14\uD835\uDF16-\uD835\uDF34\uD835\uDF36-\uD835\uDF4E \uD835\uDF50-\uD835\uDF6E\uD835\uDF70-\uD835\uDF88\uD835\uDF8A-\uD835\uDFA8\uD835\uDFAA-\uD835\uDFC2\uD835\uDFC4-\uD835\uDFCB\uD83A\uDC00-\uD83A\uDCC4\uD83B\uDE00-\uD83B\uDE03 \uD83B\uDE05-\uD83B\uDE1F\uD83B\uDE21\uD83B\uDE22\uD83B\uDE24\uD83B\uDE27\uD83B\uDE29-\uD83B\uDE32\uD83B\uDE34-\uD83B\uDE37\uD83B\uDE39\uD83B\uDE3B\uD83B\uDE42\uD83B\uDE47\uD83B\uDE49\uD83B\uDE4B\uD83B\uDE4D-\uD83B\uDE4F \uD83B\uDE51\uD83B\uDE52\uD83B\uDE54\uD83B\uDE57\uD83B\uDE59\uD83B\uDE5B\uD83B\uDE5D\uD83B\uDE5F\uD83B\uDE61\uD83B\uDE62\uD83B\uDE64\uD83B\uDE67-\uD83B\uDE6A\uD83B\uDE6C-\uD83B\uDE72\uD83B\uDE74-\uD83B\uDE77\uD83B\uDE79-\uD83B\uDE7C \uD83B\uDE7E\uD83B\uDE80-\uD83B\uDE89\uD83B\uDE8B-\uD83B\uDE9B\uD83B\uDEA1-\uD83B\uDEA3\uD83B\uDEA5-\uD83B\uDEA9\uD83B\uDEAB-\uD83B\uDEBB\uD840\uDC00-\uD869\uDED6\uD869\uDF00-\uD86D\uDF34 \uD86D\uDF40-\uD86E\uDC1D\uD86E\uDC20-\uD873\uDEA1\uD87E\uDC00-\uD87E\uDE1D";
        // IDENTIFIERPARTPART does not include supplementary characters that reach above U+FFFF, but those are used very seldom in practice
        final String IDENTIFIERPARTPART = "0-9_\u0300-\u036F\u0483-\u0487\u0591-\u05BD\u05BF\u05C1\u05C2\u05C4\u05C5\u05C7\u0610-\u061A\u064B-\u0669\u0670\u06D6-\u06DC\u06DF-\u06E4\u06E7\u06E8\u06EA-\u06ED\u06F0-\u06F9\u0711\u0730-\u074A\u07A6-\u07B0\u07C0-\u07C9\u07EB-\u07F3\u0816-\u0819\u081B-\u0823\u0825-\u0827\u0829-\u082D\u0859-\u085B\u08E3-\u0903\u093A-\u093C\u093E-\u094F\u0951-\u0957\u0962\u0963\u0966-\u096F\u0981-\u0983\u09BC\u09BE-\u09C4\u09C7\u09C8\u09CB-\u09CD\u09D7\u09E2\u09E3\u09E6-\u09EF\u0A01-\u0A03\u0A3C\u0A3E-\u0A42\u0A47\u0A48\u0A4B-\u0A4D\u0A51\u0A66-\u0A71\u0A75\u0A81-\u0A83\u0ABC\u0ABE-\u0AC5\u0AC7-\u0AC9\u0ACB-\u0ACD\u0AE2\u0AE3\u0AE6-\u0AEF\u0B01-\u0B03\u0B3C\u0B3E-\u0B44\u0B47\u0B48\u0B4B-\u0B4D\u0B56\u0B57\u0B62\u0B63\u0B66-\u0B6F\u0B82\u0BBE-\u0BC2\u0BC6-\u0BC8\u0BCA-\u0BCD\u0BD7\u0BE6-\u0BEF\u0C00-\u0C03\u0C3E-\u0C44\u0C46-\u0C48\u0C4A-\u0C4D\u0C55\u0C56\u0C62\u0C63\u0C66-\u0C6F\u0C81-\u0C83\u0CBC\u0CBE-\u0CC4\u0CC6-\u0CC8\u0CCA-\u0CCD\u0CD5\u0CD6\u0CE2\u0CE3\u0CE6-\u0CEF\u0D01-\u0D03\u0D3E-\u0D44\u0D46-\u0D48\u0D4A-\u0D4D\u0D57\u0D62\u0D63\u0D66-\u0D6F\u0D82\u0D83\u0DCA\u0DCF-\u0DD4\u0DD6\u0DD8-\u0DDF\u0DE6-\u0DEF\u0DF2\u0DF3\u0E31\u0E34-\u0E3A\u0E47-\u0E4E\u0E50-\u0E59\u0EB1\u0EB4-\u0EB9\u0EBB\u0EBC\u0EC8-\u0ECD\u0ED0-\u0ED9\u0F18\u0F19\u0F20-\u0F29\u0F35\u0F37\u0F39\u0F3E\u0F3F\u0F71-\u0F84\u0F86\u0F87\u0F8D-\u0F97\u0F99-\u0FBC\u0FC6\u102B-\u103E\u1040-\u1049\u1056-\u1059\u105E-\u1060\u1062-\u1064\u1067-\u106D\u1071-\u1074\u1082-\u108D\u108F-\u109D\u135D-\u135F\u1712-\u1714\u1732-\u1734\u1752\u1753\u1772\u1773\u17B4-\u17D3\u17DD\u17E0-\u17E9\u180B-\u180D\u1810-\u1819\u18A9\u1920-\u192B\u1930-\u193B\u1946-\u194F\u19D0-\u19D9\u1A17-\u1A1B\u1A55-\u1A5E\u1A60-\u1A7C\u1A7F-\u1A89\u1A90-\u1A99\u1AB0-\u1ABD\u1B00-\u1B04\u1B34-\u1B44\u1B50-\u1B59\u1B6B-\u1B73\u1B80-\u1B82\u1BA1-\u1BAD\u1BB0-\u1BB9\u1BE6-\u1BF3\u1C24-\u1C37\u1C40-\u1C49\u1C50-\u1C59\u1CD0-\u1CD2\u1CD4-\u1CE8\u1CED\u1CF2-\u1CF4\u1CF8\u1CF9\u1DC0-\u1DF5\u1DFC-\u1DFF\u203F\u2040\u2054\u20D0-\u20DC\u20E1\u20E5-\u20F0\u2CEF-\u2CF1\u2D7F\u2DE0-\u2DFF\u302A-\u302F\u3099\u309A\uA620-\uA629\uA66F\uA674-\uA67D\uA69E\uA69F\uA6F0\uA6F1\uA802\uA806\uA80B\uA823-\uA827\uA880\uA881\uA8B4-\uA8C4\uA8D0-\uA8D9\uA8E0-\uA8F1\uA900-\uA909\uA926-\uA92D\uA947-\uA953\uA980-\uA983\uA9B3-\uA9C0\uA9D0-\uA9D9\uA9E5\uA9F0-\uA9F9\uAA29-\uAA36\uAA43\uAA4C\uAA4D\uAA50-\uAA59\uAA7B-\uAA7D\uAAB0\uAAB2-\uAAB4\uAAB7\uAAB8\uAABE\uAABF\uAAC1\uAAEB-\uAAEF\uAAF5\uAAF6\uABE3-\uABEA\uABEC\uABED\uABF0-\uABF9\uFB1E\uFE00-\uFE0F\uFE20-\uFE2F\uFE33\uFE34\uFE4D-\uFE4F\uFF10-\uFF19\uFF3F\u000101FD";
        if(identifierPartsString == null) {
            identifierPartsString = new AbstractString(new RegExp("["+IDENTIFIERHEAD + IDENTIFIERPARTPART+"]*").toAutomaton(true));
        }
        return identifierPartsString;
    }

    public static AbstractString getStringOther() {
        if (stringOther == null) {
            stringOther = getAnyNumberString().leastUpperBound(getIdentifierPartsString()).getComplement();
        }
        return stringOther;
    }

    public AbstractString(String s){
        dfa = BasicAutomata.makeString(s);
    }

    private AbstractString(Automaton a) {
        dfa = a;
    }

    //-------------------------------------------------------------------
    public AbstractString intersect(AbstractString a){
        return new AbstractString(this.dfa.intersection(a.dfa));
    }

    public AbstractString leastUpperBound(AbstractString a) {
        if (a==null) {
            return this;
        }
        if (a == this || a.equals(this)) {
            return this;
        }
        return new AbstractString(this.dfa.union(a.dfa));
    }

    public AbstractString widen(AbstractString a) {
        Automaton r = SpecialOperations.widen(dfa, a.dfa);
        r.minimize();
        if (r.equals(dfa)) {
            return this;
        } else {
            return new AbstractString(r);
        }

    }

    public boolean run(String s) {
        return dfa.run(s);
    }

    /**
     * Corresponds to the partial ordering on our abstract domain
     * Our notion of less-than corresponds to language subset, ie.
     * A <= B iff L(A) subseteq L(B)
     * @return true if this is less than or equal to @other, false if it is greater, or if there is no ordering.
     */
    //---------------------------------------------------------------
    // Test this one
    //---------------------------------------------------------------
    public boolean isSubset(AbstractString other) {
        return other != null && dfa.equals(dfa.intersection(other.dfa));
    }
    //---------------------------------------------------------------
    public boolean hasIntersection(AbstractString other){
        return !BasicOperations.isEmpty(dfa.intersection(other.dfa));
    }

    public boolean isEmpty(){
        return dfa.isEmpty();
    }

    /**
     *
     * @return If the dfa derives only one strin, then that string. Otherwise, null.
     */
    public String stringValue(){
        Set<String> language = dfa.getFiniteStrings(1); // gets finite strings, returns null if there are more than 1.
        if (language != null && language.size() == 1) { // We use this instead of getSingleton because getSingleton's
            return language.iterator().next();          // return value depends on the form of the dfa
        }
        return null;
    }

    public boolean isSingleString(){
        return stringValue() != null;
    }

    /**
     * This is equivalent to *run* but is included for backward compatibility
     * @param s
     */
    public boolean equals(String s) {
        return s.equals(stringValue());
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof AbstractString) {
            return this.equals((AbstractString)o);
        } else {
            return false;
        }
    }

    public boolean equals(AbstractString s){
        return dfa.equals(s.dfa);
    }
    // Abstract Operations

    public static AbstractString concat(AbstractString a, AbstractString b) {
        if (a==null) {
            return b;
        } else if (b == null) {
            return a;
        }
        return new AbstractString(BasicOperations.concatenate(a.dfa,b.dfa));
    }

    public AbstractString concat(AbstractString b) {
        dfa = concat(this, b).dfa;
        return this;
    }

    public AbstractString contains(char c) {
        return new AbstractString(
                BasicOperations.intersection(dfa,
                BasicAutomata.makeAnyString()
                             .concatenate(BasicAutomata.makeChar(c)
                             .concatenate(BasicAutomata.makeAnyString()))));
    }

    public AbstractString getComplement() {
        return new AbstractString(BasicOperations.complement(dfa));
    }

    public int length() {return dfa.getNumberOfStates();}

    //---------------------------------------------------------------
    // Will fail until implementation
    //---------------------------------------------------------------
    public boolean startsWith(AbstractString s) {
        throw new NotImplementedException();
    }
    public char charAt(int i) { throw new NotImplementedException();}

    public AbstractString substring(int b, int e){
        throw new NotImplementedException();
    }
    public AbstractString substring(int b) {
        throw new NotImplementedException();
    }
    //---------------------------------------------------------------

    @Override
    public String toString() {
        if(isSingleString()) {
            return "\"" + stringValue() + "\"";
        } else if (equals(AbstractString.uIntString())) {
            return "[UInt string]";
        } else if (equals(AbstractString.otherNumString())) {
            return "[Other number string]";
        } else if (equals(AbstractString.getAnyNumberString())) {
            return "[Any number string]";
        } else if (equals(getIdentifierPartsString()) || equals(getIdentifierString())) {
            return "[Identifier parts string]";
        } else if (equals(getStringOther())) {
            return "[String other]";
        } else if (equals(getAnyString())){
            return "[Any string]";
        } else {
            Set<String> derivations = dfa.getFiniteStrings(10);
            if (derivations != null) {
                StringBuilder b = new StringBuilder().append("[");
                for (String s : derivations) {
                    b.append("\"" + s + "\", ");
                }
                b.delete(b.length()-2,b.length()).append("]");
//                b.delete(b.length()-2,b.length());
//                if(b.charAt(b.length()-1) != ']') {
//                    b.append("]");
//                }
//                if(b.charAt(1) == '[') {
//                    b.deleteCharAt(1);
//                }

                return b.toString();
            } else {
                System.out.println(dfa);
                return "[Many possible strings (" + dfa.getNumberOfStates()+ " states), such as \"" + dfa.getShortestExample(true) + "\"]";
            }
        }
    }

    public Automaton getDfa() {
        return dfa;
    }

    public AbstractString getUIntString() {
        return uIntString;
    }

}
