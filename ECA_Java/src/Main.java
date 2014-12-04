import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	
	private PrintWriter writer;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Path ttPath = Paths.get("../treetagger");
		DirectoryStream<Path> stream = Files.newDirectoryStream(ttPath);

		Path outputArff = Paths.get("tt_java/output.arff");
		Files.write(outputArff, writeHeader().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		try {
			Iterator<Path> iterator = stream.iterator();
			int i = 0;
				while (iterator.hasNext() && i < 10) {
					Path p = iterator.next();
//					System.out.println(p.getFileName());
					toArff(p);
					i++;
				}
		} finally {
			stream.close();
		}
	}

	public static void toArff(Path p) throws IOException, FileNotFoundException {
		Files.createDirectories(Paths.get("tt_java/"));
		Path outputArff = Paths.get("tt_java/output.arff");
		
		

		List<String> smallFilesLines = Files.readAllLines(p, StandardCharsets.UTF_8);
		String noteCommentaire = new String();
		String textCommentaire = new String();
		boolean commentaire = false;
		
		for (String oneLine : smallFilesLines) {
		/**
		 * @TODO : Parser le fichier txt en arff
		 */
			
			String data[] = oneLine.split("\t");

			
			if (data[0].startsWith("TitreCommentaire")) {
				commentaire = true;
			}
			
			if (data[0].startsWith("DateCommentaire")) {
				commentaire = false;
			}
			
			if (commentaire) {
				textCommentaire += data[0] + " ";
			}
			
			if (data[0].startsWith("NoteCommentaire")) {
				System.out.print(data[0] + " ==== ");
				System.out.println(data[0].split(":")[1]);
				noteCommentaire = data[0].split(":")[1] + ",";
			}

			
		}
		
		textCommentaire += "\n";
		
		
		Files.write(outputArff, noteCommentaire.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

		Files.write(outputArff, textCommentaire.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		
	}
	
//	public static void toArff(Path p) throws IOException, FileNotFoundException {
//
//		Files.createDirectories(Paths.get("tt_java/"));
//		Path outputArff = Paths.get("tt_java/output.arff");
//
//		BufferedReader br = null;
//		 
//		try {
//	 
//			String line;
//	 
////			br = new BufferedReader(new FileReader(outputArff.toString()));	 
////			while ((line = br.readLine()) != null) {
////	 
//////			   StringTokenizer stringTokenizer = new StringTokenizer(line, "\t");
////			   String[] data = line.split("\t");
////			   
////			   System.out.println(data[0]+ " "+ data[1]);
////	 
////			   while (stringTokenizer.hasMoreTokens()) {
////	 
////			    String mot = stringTokenizer.nextToken().trim();
////			    String id = stringTokenizer.nextToken().trim();
//////			    String mot2 = stringTokenizer.nextToken().toString();
////	 
////				StringBuilder sb = new StringBuilder();
////				sb.append(mot);
////				sb.append(id);
//////				sb.append(mot2);
////				sb.append("\n*******************\n");
////				System.out.println(sb);
//				
////				Files.write(outputArff, sb.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
//			   }
////			}
//			System.out.println("Done");
//	 
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (br != null)
//					br.close();
//	 
//			} catch (IOException ex) {
//				ex.printStackTrace();
//			}
//		}
//	 
//		}

	
	public static String writeHeader() {
		// @relation nom_arff
		// @attribute class {mauvais, neutre, bien}
		// @attribute text String
		
		// @data
		return "@relation play_store_comments\n"
				+ "@attribute class {mauvais, neutre, bien}\n"
				+ "@attribute text String\n"
				+ "@data\n";
	}
}
