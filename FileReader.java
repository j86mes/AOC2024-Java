import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileReader {

	RandomAccessFile _file;
	public FileReader(String fileLocation) throws FileNotFoundException{
		_file = new RandomAccessFile(fileLocation, "r");
	}

	public String nextLine() throws IOException{
		return _file.readLine();
	}

	public void close() throws IOException{
		_file.close();
	}
}
