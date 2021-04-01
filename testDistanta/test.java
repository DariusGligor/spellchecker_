package testDistanta;

import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;

public class test {
	public static int minim(int a, int b, int c) {
		int min = a;
		if (min > b)
			min = b;
		if (min > c)
			min = c;
		return min;
	}

	public static boolean verific(String lit1, String lit2) {
		if ((lit1.equals("a") && lit2.equals("ă")) || (lit1.equals("ă") && lit2.equals("a")))
			return true;
		if ((lit1.equals("a") && lit2.equals("â")) || (lit1.equals("â") && lit2.equals("a")))
			return true;
		if ((lit1.equals("î") && lit2.equals("i")) || (lit1.equals("i") && lit2.equals("î")))
			return true;
		if ((lit1.equals("s") && lit2.equals("ș")) || (lit1.equals("ș") && lit2.equals("s")))
			return true;
		if ((lit1.equals("t") && lit2.equals("ț")) || (lit1.equals("ț") && lit2.equals("t")))
			return true;

		return false;
	}

	public static int distanta(String sText1, String sText2) {
		ArrayList<Integer> v1 = new ArrayList<Integer>();
		ArrayList<Integer> v2 = new ArrayList<Integer>();
		int ok = 0;
		int k = 0;
		int i;
		v1.add(k);
		// mergem cu unu in fata
		for (i = 0; i < sText1.length(); i++) {
			k++;
			v1.add(k);
			v2.add(0);
		}
		int j;
		for (i = 1; i < sText2.length(); i++) {
			for (j = 0; j < sText1.length(); j++) {
				if (j == 0) {
					v2.set(j, v1.get(j) + 1);
				} else {
					if (sText2.codePointAt(i) == (sText1.codePointAt(j))) {
						v2.set(j, minim(v2.get(j - 1), v1.get(j), v1.get(j - 1)));
					} else {
						if (isDiacritics(sText1.codePointAt(j), sText2.codePointAt(i)) == true) {
							ok = 1;
						} else {
							v2.set(j, minim(v2.get(j - 1), v1.get(j), v1.get(j - 1)) + 1);
						}
					}
				}

			}
			v1.clear();
			v1.addAll(v2);
		}
		int z = v1.get(v1.size() - 1);
		if ((z == 0) & (sText2.length() != sText1.length())) {
			z = sText1.length() - sText2.length();
			if (z > 0)
				return z;
			else
				return -z;
		} else if (z == 0) {
			for (k = 0; k < sText1.length(); k++)
				if (sText1.codePointAt(k) != sText2.codePointAt(k)
						&& isDiacritics(sText1.codePointAt(k), sText2.codePointAt(k)) == false)
					z++;
		}
		if (ok == 1)
			return -1;
		return z;
	}

	public static boolean isDiacritics(final int chB, final int chS) {
		if ((chB == 'a' || chB == 'A')
				&& (chS == '\u0103' || chS == '\u0102' || chS == '\u00e2' || chS == '\u00c2' || chS == '\u01CE')) {
			return true;
		} else if ((chB == 's' || chB == 'S')
				&& (chS == '\u015f' || chS == '\u015e' || chS == '\u0219' || chS == '\u0218')) {
			return true;
		} else if ((chB == 't' || chB == 'T')
				&& (chS == '\u0163' || chS == '\u0162' || chS == '\u021b' || chS == '\u021a')) {
			return true;
		} else if ((chB == 'i' || chB == 'I') && (chS == '\u00ce' || chS == '\u00ee')) {
			return true;
		} else if ((chS == 'a' || chS == 'A') && (chB == '\u0103' || chB == '\u0102')) {
			return true;
		} else if ((chS == 'a' || chS == 'A') && (chB == '\u00e2' || chB == '\u00c2' || chB == '\u01CE')) {
			return true;
		} else if ((chS == 's' || chS == 'S') && (chB == '\u015f' || chB == '\u015e')) {
			return true;
		} else if ((chS == 's' || chS == 'S') && (chB == '\u0219' || chB == '\u0218')) {
			return true;
		} else if ((chS == 't' || chS == 'T')
				&& (chB == '\u0163' || chB == '\u0162' || chB == '\u021b' || chB == '\u021a')) {
			return true;
		} else if ((chS == 'i' || chS == 'I') && (chB == '\u00ce' || chB == '\u00ee')) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Introduceti primul cuvant");
		String text = "\0";
		text = text + scan.nextLine();
		System.out.println("Introduceti cel de al doilea cuvant cuvant");
		String text2 = "\0";
		text2 = text2 + scan.nextLine();
		int z = distanta(text, text2);
		if (z == 0)
			System.out.println("Cele doua cuvinte sunt similare");
		else if (z == -1)
			System.out.println("Cele doua cuvinte sunt similare,dar unul dintre ele nu are diacritice");
		else
			System.out.print("Cele doua cuvinte nu sunt similare si au distanta de editare: " + z);
	}
}