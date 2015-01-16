package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Main {
	
	public Main() {
		writeToFile("This text is appended to the end of the file", "Other.txt");
		readFile("Test.txt");
	}

	public static void main(String[] args) {
		Main m = new Main();
	}
	
	public void readFile(String fileName){
		try{
			
			/*
			 * creates a BufferedReader object for reading files. It takes a FileReader object. We can just instantiate a new FileReader
			 * object to do the work for us. The argument that the FileReader takes is a file to be read.
			 * We just instantiate a new File object. The argument that the File object takes is the name of the file.
			 * If you create a file in the Eclipse workspace (by right clicking on the project name and then clicking new,
			 * then clicking the File option) then the file name is pretty easy. Eclipse will automatically look in the workspace
			 * for files to be loaded. If we saved the file to our desktop we have to use something different. We need to exact path
			 * of the object in that case. For example, on my computer, the file path location to my desktop would be:
			 * Macintosh HD/Users/blakemills/Desktop/filename.txt.
			 * In the case that we put the file in the Eclipse workspace, the file name would simply be filename.txt
			*/
			
			BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
			
			/*
			 * We need the String line variable to store the file in terms of lines. The files are read a line at a time,
			 * meaning that after each line is read the readLine method (reader.readLine()) will return the string that
			 * represents that line, hence the reason we store it in a string.
			 */
			
			String line;
			
			/*
			 * If you notice inside of the while loop, the line variable is being assigned inside of seperate parenthesis.
			 * This is a nice thing to know because it allows the variable to be assigned to the next line that is read
			 * continuously (as long as the while loop doesn't fail). This means that if the reader reaches the end of the file,
			 * there won't be anything to be read. In that case, the buffered reader returns a null string, and the value of line
			 * would ne null. In that case, we don't assign the variable. That is becasue we have said that the line variable will
			 * only be equal to the next line as long as the next line doesn't equal null.
			 */
			
			while ((line = reader.readLine()) != null){ 
				System.out.println(line); // Prints the line each time. Line will change after this print to something else.
			}
			
			/*
			 * When we create the BufferedReader object we also open the reader object. This uses up system resources.
			 * The system resources are the things that your computer needs to use to keep running. It is always a good idea to
			 * close the reader after the reading process has ended so that the reader object doesn't keep the memory to the 
			 * system resources. After that, the memory is returned to the system and the system can reuse it to
			 * do its bidding.
			 */
			
			reader.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	
	public void writeToFile(String text, String fileName){
		try{
			
			/*
			 * We create a new file with the file name that we have specified
			 */
			
			File f = new File(fileName);
			
			/*
			 * This next part is important. We create the file object explicity (meaning that we create the object "f"
			 * of type File, instead of calling new File(fileName) when we want to write to it) because the File class
			 * has the ability to check to see if a file already exists with the file name that we have told it to create.
			 * In the case that it doesn't, the new file is created. In the case that it does, the file is passed along to the
			 * writting process.
			 */
			
			if (!f.exists()){
				f.createNewFile();
			}
			
			/*
			 * This is nearly identical to the BufferedReader that we created above. The only difference is that it takes
			 * a FileWriter object instead of a FileReader object. The FileWriter takes 2 arguments. The first one is the file that
			 * we want to write to. We don't call new File(fileName) here becuase that would be redundant. We already have a file that
			 * we created a while ago in the earlier part of this method, so we should use that. The second is kind of important.
			 * It takes a boolean. If the value is true, the writer will not erase what is in the file when it goes to write
			 * data to it. If we put false, every time that we wrote to the file, all of the other data would be erased. By default,
			 * meaning if no boolean is put as the second argument (method overloading), the value is false, and all data will be erased
			 * before new data is written to it.
			 * 
			 */
			
			BufferedWriter wr = new BufferedWriter(new FileWriter(f, true));
			wr.write(text + "\n"); //Writes the text to the file. The "\n" means that we put a new line at the end of the text.
			wr.close(); // Always close for the same reason as stated above.
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}

