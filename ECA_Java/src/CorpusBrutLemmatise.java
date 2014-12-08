import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.List;

public class CorpusBrutLemmatise {
	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Path ttPath = Paths.get("../treetagger");
		DirectoryStream<Path> stream = Files.newDirectoryStream(ttPath);

		Path outputArff = Paths.get("tt_java/outputLemm.arff");
		Files.write(outputArff, writeHeader().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		try {
			Iterator<Path> iterator = stream.iterator();
				while (iterator.hasNext()) {
					Path p = iterator.next();
					toArff(p);
				}
		} finally {
			stream.close();
		}
	}

	public static void toArff(Path p) throws IOException, FileNotFoundException {
		Files.createDirectories(Paths.get("tt_java/"));
		Path outputArff = Paths.get("tt_java/outputLemm.arff");
		
		

		List<String> smallFilesLines = Files.readAllLines(p, StandardCharsets.UTF_8);
		String noteCommentaire = new String();
		String textCommentaire = new String();
		boolean commentaire = false;
		String test = new String();
		
		for (String oneLine : smallFilesLines) {
		/**
		 * @TODO : Parser le fichier txt en arff
		 */
			
			String data[] = oneLine.split("\t");

			
			if (data[0].startsWith(":") && test.equals("TitreCommentaire")) {
				commentaire = true;
				textCommentaire += "\'";
			}
			
			if (data[0].startsWith("DateCommentaire")) {
				commentaire = false;
			}
			
			if (commentaire) {
				if(!data[2].equals("<unknown>")) {
					textCommentaire += data[2].replace("\'", "\\\'") + " ";
					//System.out.println(data[2]);
				}
			}
			
			if (data[0].startsWith("NoteCommentaire")) {
				//System.out.print(data[0] + " ==== ");
				//System.out.println(data[0].split(":")[1]);
				//noteCommentaire = data[0].split(":")[1] + ",";
				switch(Integer.parseInt(data[0].split(":")[1])) {
				case 1: noteCommentaire = "mauvais, ";
				break;
				
				case 2: noteCommentaire = "mauvais, ";
				break;
				
				case 3: noteCommentaire = "neutre, ";
				break;
				
				case 4: noteCommentaire = "bien, ";
				break;
				
				case 5: noteCommentaire = "bien, ";
				break;
				
				default: noteCommentaire = "neutre, ";
				break;
				
				}
				textCommentaire += "\'";
			}
			
			test = data[0];
			
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
				+ "@attribute ps_class {mauvais, neutre, bien}\n"
				+ "@attribute text String\n"
				+ "@data\n";
	}
}
