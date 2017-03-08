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
public class Myai {
  public static void main (String args[]) {

//候補からランダムに答えを選んで返す
//TODO? モンテカルロ木探索みたいにしたかった

//初期化:Myfwと同じ
	HashMap<String, Integer> hashmap = new HashMap<String, Integer>();
	HashMap<Character, HashMap<String, Integer>> CharToStringsMap = new HashMap<Character,HashMap<String, Integer>>();
	Character firstChar;
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


	Map.Entry<String,Integer> ansMap;
	String ans;
	String saigo = args[0];
	Character targetChar;
	targetChar = saigo.charAt(saigo.length()-1); 
	Iterator<Map.Entry<String,Integer>> iteratorForPickAns;
	HashMap<String, Integer> ChoicesOfAns;
	Integer hoge_prevcount;
	Integer piyo_prevcount;
	ArrayList<String> ramdamAnsChoices = new ArrayList<String>();
	Random rnd1 = new Random();
//有効な答えがある条件
	if (CharToStringsMap.containsKey(targetChar) && !(CharToStringsMap.get(targetChar).isEmpty())){
//答えを選ぶ
		ChoicesOfAns = CharToStringsMap.get(targetChar);
	        for(Map.Entry<String, Integer> e : ChoicesOfAns.entrySet()) {
			for (int i = 0; i < e.getValue(); i++){
				ramdamAnsChoices.add(e.getKey());
			}
		}
		ans = ramdamAnsChoices.get(rnd1.nextInt(ramdamAnsChoices.size()));
		System.out.println(ans);
//さっき答えた単語を消す処理
		hoge_prevcount = CharToStringsMap.get(ans.charAt(0)).get(ans);
		if(hoge_prevcount - 1 == 0){
				CharToStringsMap.get(ans.charAt(0)).remove(ans);
			}else{
				CharToStringsMap.get(ans.charAt(0)).put(ans,hoge_prevcount - 1);
		}
		piyo_prevcount = hashmap.get(ans);
		if(piyo_prevcount - 1 == 0){
			hashmap.remove(ans);
		}else{
			hashmap.put(ans,piyo_prevcount - 1);
		}
//答えがあるときの処理終わり
	}else{
//答えられる単語がないとき
		System.out.println("damn");
	}




  }
}
