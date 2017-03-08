import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import java.lang.Character;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Boolean;
public class Myfw {
  public static void main (String args[]) {
try{
	if(args.length < 3){
		System.out.println("引数が足りません");
		System.exit(1);
	}
//初期化
//与えられた文字に対して、最初の一文字がその与えられた文字に一致する文字列の検索を処理を無駄なくしたい
//答えることができる文字列の集合から、文字列を消去する処理（一度答えた文字は使えなくする）処理を無駄なくしたい
//hashmapは(key 答えられる文字列： value その文字列がいくつあるか)
//CharToStringsMapは（key 文字:value (key その文字を先頭にする文字列：value その文字列がいくつあるか) ）

//（早すぎる最適化感がある）実際あまり速くならなさそう

	String prevAns;
	prevAns = args[2];
	Pattern matubiKaigyou = Pattern.compile("\n$");
	HashMap<String, Integer> hashmap = new HashMap<String, Integer>();
	HashMap<Character, HashMap<String, Integer>> CharToStringsMap = new HashMap<Character,HashMap<String, Integer>>();
	Character firstChar;
	Boolean isSente = true;
	
	
//引数をhashmapとCharToStringsMapの構造に変換
	//argsのうち回答候補単語はargs[3]から
	for (int i = 1; i < args.length; i++){

		firstChar = args[i].charAt(0);
		
		if(CharToStringsMap.containsKey(firstChar) && CharToStringsMap.get(firstChar).containsKey(args[i])){
			Integer A_prevcount = CharToStringsMap.get(firstChar).get(args[i]);
			CharToStringsMap.get(firstChar).put(args[i], A_prevcount + 1);
		}else if(CharToStringsMap.containsKey(firstChar)){
			CharToStringsMap.get(firstChar).put(args[i],1);
		}else{
			HashMap<String, Integer> coll = new HashMap<String, Integer>();
			coll.put(args[i],1);
			CharToStringsMap.put(firstChar,coll);
		}

		if(hashmap.containsKey(args[i])){
			Integer prevcount = hashmap.get(args[i]);
			hashmap.put(args[i],prevcount + 1);
		}else{
			hashmap.put(args[i],1);
		}
	}
//初期化終わり


	Integer hoge_prevcount;
	Integer piyo_prevcount;


	ArrayList<String> nextargslist = new ArrayList<String>();

	String text;
	String text2;
	
	Process process;
	InputStream is;
	InputStreamReader isr;
	BufferedReader reader;
	StringBuilder builder;
	int c;
	
	String outText = "";
	String winner = "";

while(true){

	nextargslist = new ArrayList<String>();
	if(isSente){
		nextargslist.add(args[0]);
	}else{
		nextargslist.add(args[1]);
	}
	nextargslist.add(prevAns);

//hashmap（回答候補）から引数を生成
       for(Map.Entry<String, Integer> e : hashmap.entrySet()) {
		for (int i = 0; i < e.getValue(); i++){
			nextargslist.add(e.getKey());
		}
	}
//
	process = new ProcessBuilder(nextargslist).start();
	is = process.getInputStream();
	isr = new InputStreamReader(is, "UTF-8");
	reader = new BufferedReader(isr);
	builder = new StringBuilder();

	while ((c = reader.read()) != -1) {
		builder.append((char)c);
	}

	// 実行結果を格納
	text = builder.toString();

	int ret = process.waitFor();
//戻り値の最後の改行を削除
	text2 = matubiKaigyou.matcher(text).replaceAll("");

//合法手の条件
	if (hashmap.containsKey(text2) && prevAns.charAt(prevAns.length()-1) == text2.charAt(0)){
//
		if(isSente){
			outText = "FIRST (OK): ";
			outText = outText + text2;
		}else{
			outText = "SECOND (OK): ";
			outText = outText + text2;
		}

		prevAns = text2;

//使われた単語を合法手の候補から消す
		hoge_prevcount = CharToStringsMap.get(prevAns.charAt(0)).get(prevAns);
		if(hoge_prevcount - 1 == 0){
				CharToStringsMap.get(prevAns.charAt(0)).remove(prevAns);
			}else{
				CharToStringsMap.get(prevAns.charAt(0)).put(prevAns,hoge_prevcount - 1);
		}
		piyo_prevcount = hashmap.get(prevAns);
		if(piyo_prevcount - 1 == 0){
			hashmap.remove(prevAns);
		}else{
			hashmap.put(prevAns,piyo_prevcount - 1);
		}
		
//
		isSente = !isSente;
		System.out.println(outText);
	}else{
		if(isSente){
			outText = "FIRST (NG): ";
			outText = outText + text2;
			winner = "SECOND";
		}else{
			outText = "SECOND (NG): ";
			outText = outText + text2;
			winner = "FIRST";
		}
		isSente = !isSente;
		System.out.println(outText);
		System.out.println("WIN - " + winner);
		break;
	}
	
}

} catch (IOException | InterruptedException e) {
	e.printStackTrace();
}

  }
}
