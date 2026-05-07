import java.io.*;
import java.util.*;

public class Main {
    static String S;
    static int K, M;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        S = br.readLine();

        StringTokenizer st = new StringTokenizer(br.readLine());
        K = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        HashMap<Long, Integer> map = new HashMap<>();

        long hash = 0;
        long base = 2;

        // 처음 K개 해시 만들기
        for (int i = 0; i < K; i++) {
            hash = hash * base + (S.charAt(i) - '0' + 1);
        }

        map.put(hash, 1);

        if (M == 1) {
            System.out.println(1);
            return;
        }

        long pow = 1;
        for (int i = 0; i < K - 1; i++) {
            pow *= base;
        }

        // 슬라이딩 윈도우
        for (int i = K; i < S.length(); i++) {
            int left = S.charAt(i - K) - '0' + 1;
            int right = S.charAt(i) - '0' + 1;

            hash -= left * pow;
            hash = hash * base + right;

            map.put(hash, map.getOrDefault(hash, 0) + 1);

            if (map.get(hash) >= M) {
                System.out.println(1);
                return;
            }
        }

        System.out.println(0);
    }
}