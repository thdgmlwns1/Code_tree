import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        String s = br.readLine();

        if (N % 2 == 1) {
            System.out.println("No");
            return;
        }

        int in = 0;
        int out = 0;
        int question = 0;

        for (int i = 0; i < N; i++) {
            char c = s.charAt(i);

            if (c == '(') {
                in++;
            } else if (c == ')') {
                out++;
            } else {
                question++;
            }
        }

        int needIn = N / 2 - in;
        int needOut = N / 2 - out;

        if (needIn < 0 || needOut < 0 || needIn + needOut != question) {
            System.out.println("No");
            return;
        }

        int count = 0;

        for (int i = 0; i < N; i++) {
            char c = s.charAt(i);

            if (c == '(') {
                count++;
            } else if (c == ')') {
                count--;
            } else {
                if (needIn > 0) {
                    count++;
                    needIn--;
                } else {
                    count--;
                    needOut--;
                }
            }

            if (count < 0) {
                System.out.println("No");
                return;
            }
        }

        if (count == 0) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }
}