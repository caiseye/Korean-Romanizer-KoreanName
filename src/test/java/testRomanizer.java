import com.example.terry.KoreanRomanizer;

import java.util.List;
import java.util.Scanner;

public class testRomanizer {
    public static void main(String... args) {
        while(true) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("한국어 텍스트를 입력하세요: ");
            String name = scanner.nextLine();
            System.out.println("추천이름");
            List<String> recommendedNames = KoreanRomanizer.romanizeSurName(name);
            for (Object recommendedName : recommendedNames) {
                System.out.println(recommendedName);
            }
        }
    }
}
