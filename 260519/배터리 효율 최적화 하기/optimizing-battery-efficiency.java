import java.io.*;
import java.util.*;

public class Main {
    static int N, M;
    static int[][] map;
    static boolean[][] selected;
    static List<List<int[]>> modules = new ArrayList<>();

    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    static int answer = Integer.MIN_VALUE;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        selected = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0, 0);

        for (int i = 0; i < modules.size(); i++) {
            for (int j = i + 1; j < modules.size(); j++) {
                checkTwoModules(modules.get(i), modules.get(j));
            }
        }

        System.out.println(answer);
    }

    // 전체 칸 중에서 5칸 고르기
    static void dfs(int start, int count) {
        if (count == 5) {
            if (isConnected()) {
                saveModule();
            }
            return;
        }

        for (int i = start; i < N * M; i++) {
            int r = i / M;
            int c = i % M;

            selected[r][c] = true;
            dfs(i + 1, count + 1);
            selected[r][c] = false;
        }
    }

    // 선택한 5칸이 연결되어 있는지 확인
    static boolean isConnected() {
        boolean[][] visited = new boolean[N][M];
        Queue<int[]> q = new ArrayDeque<>();

        outer:
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (selected[i][j]) {
                    q.offer(new int[]{i, j});
                    visited[i][j] = true;
                    break outer;
                }
            }
        }

        int count = 0;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0];
            int y = cur[1];
            count++;

            for (int d = 0; d < 4; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];

                if (nx < 0 || ny < 0 || nx >= N || ny >= M) continue;
                if (visited[nx][ny]) continue;
                if (!selected[nx][ny]) continue;

                visited[nx][ny] = true;
                q.offer(new int[]{nx, ny});
            }
        }

        return count == 5;
    }

    // 연결된 5칸짜리 모듈 저장
    static void saveModule() {
        List<int[]> module = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (selected[i][j]) {
                    module.add(new int[]{i, j});
                }
            }
        }

        modules.add(module);
    }

    // 두 모듈이 정확히 2칸 겹치는지 확인
    static void checkTwoModules(List<int[]> a, List<int[]> b) {
        Set<String> set = new HashSet<>();
        int sum = 0;

        for (int[] cell : a) {
            int r = cell[0];
            int c = cell[1];
            sum += map[r][c];
            set.add(r + "," + c);
        }

        for (int[] cell : b) {
            int r = cell[0];
            int c = cell[1];
            sum += map[r][c];
            set.add(r + "," + c);
        }

        // 5 + 5 = 10칸인데, 겹친 칸이 2개면 실제 서로 다른 칸은 8개
        if (set.size() == 8) {
            answer = Math.max(answer, sum);
        }
    }
}