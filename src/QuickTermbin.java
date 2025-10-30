import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class QuickTermbin 
{

    public static void printHelp()
    {
        StringBuilder sb = new StringBuilder("qtb <id> [-o <output_path>] [-b64] [-u]\n\n");
        sb.append("OPTIONS:\n");
        sb.append("\t-o <output_path> \tstores the file in the choosen location.\n");
        sb.append("\t-b64 \t\t\tdecodes the file before to storing it.\n");
        sb.append("\t-u \t\t\tdecodes and unzip the file.\n");
        sb.append("\t-h \t\t\tto show this message.\n");
        System.out.println(sb.toString());
    }

    public static void main(String[] args)
    {
        try 
        {
            if(args.length == 0 || args[0].equals("-h"))
            {
                printHelp();
                return;
            }

            String id = args[0], outputPath = null;
            boolean nextIsOutputPath = false, decodeBase64 = false, unzip = false;

            for(int i=1; i<args.length; i++)
            {
                String s = args[i];

                switch(s)
                {
                    case "-b64": 
                    {
                        decodeBase64 = true;
                        break;
                    }
                    case "-u":
                    {
                        decodeBase64 = true;
                        unzip = true;
                        break;
                    }
                    case "-o":
                    {
                        nextIsOutputPath = true;
                        break;
                    }
                    case "-h":
                    {
                        throw new IllegalArgumentException();
                    }
                    default:
                    {
                        if(!nextIsOutputPath) throw new IllegalArgumentException();
                        outputPath = s;
                    }
                }
            }

            if(outputPath == null){
                outputPath = "./"+id;
            }

            wget(id, outputPath);

            if(decodeBase64){
                base64Decode(outputPath, outputPath);
            }

            if(unzip){
                unzipFile(outputPath, outputPath);
            }
        } 
        catch (Exception e) 
        {
            // e.printStackTrace();
            printHelp();
        }
    }

    public static void wget(String id, String outputPath) throws IOException, InterruptedException 
    {
        String[] wgetCmd = {"wget", "https://termbin.com/"+id, "-O", outputPath};

        ProcessBuilder pb = new ProcessBuilder(wgetCmd)
            .inheritIO();
        Process wgetProc = pb.start();
        wgetProc.waitFor();
        
        if(wgetProc.exitValue() != 0)
        {
            throw new RuntimeException("Download failed!");
        }
    }

    public static void base64Decode(String filePath, String outputPath) throws IOException, InterruptedException
    {
        if(filePath.equals(outputPath))
        {
            Files.move(Path.of(filePath), Path.of(filePath+="b64"), StandardCopyOption.ATOMIC_MOVE);
        }
        String[] base64Cmd = {"base64", "-d", filePath};

        Process base64Proc = new ProcessBuilder(base64Cmd)
            .redirectOutput(new File(outputPath))
            .start();
        base64Proc.waitFor();

        if(base64Proc.exitValue() != 0)
        {
            throw new RuntimeException("Base64 decoding failed!");
        }

        Files.delete(Path.of(filePath));
    }

    public static void unzipFile(String path, String outputPath) throws IOException, InterruptedException
    {
        //L'opzione -n previene la sovrascrittura di file con lo stesso nome.
        if(path.equals(outputPath))
        {
            Files.move(Path.of(path), Path.of(path+=".zip"), StandardCopyOption.ATOMIC_MOVE);
        }
        String[] unzipCmd = {"unzip", "-n", path, "-d", outputPath};

        Process unzipProcess = new ProcessBuilder(unzipCmd)
            .inheritIO()
            .start();
        unzipProcess.waitFor();
        
        if(unzipProcess.exitValue() != 0)
        {
            throw new RuntimeException("Extraction failed.");
        }

        Files.delete(Path.of(path));
    }
}