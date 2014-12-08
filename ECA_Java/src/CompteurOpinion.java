import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CompteurOpinion {

	public static void main(String[] args) throws IOException {
		String fileName = "tt_java/outputNetLemm.arff";
		int bien = 0, neutre = 0, mauvais = 0;

		try {
			List<String> lines = Files.readAllLines(Paths.get(fileName), Charset.defaultCharset());
			for (String line : lines) {
				if (line.startsWith("bien")) {
					System.out.println(line);
					bien++;
				}
				if (line.startsWith("neutre")) {
					System.out.println(line);
					neutre++;
				}
				
				if (line.startsWith("mauvais")) {
					System.out.println(line);
					mauvais++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(bien); //2896
		System.out.println(neutre); //595
		System.out.println(mauvais); //1280
		System.out.println(bien+neutre+mauvais); //4771 Vérifie si on a rien manqué : OK

		
	}
}
