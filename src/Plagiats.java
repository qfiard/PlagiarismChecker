import java.io.*;
import java.util.*;

import structures.*;
import utilities.*;

/**
* La classe Plagiats est la classe de base du programme, qui contient la fonction main.
* @author Thomas Brichard et Quentin Fiard
*/
public class Plagiats {
	
	public static List<File> filesInDirectory(File directory)
	{
		LinkedList<File> res = new LinkedList<File>();
		
		File[] inDirectory = directory.listFiles();
		
		for(File file : inDirectory)
		{
			if(file.isDirectory())
			{
				res.addAll(filesInDirectory(file));
			}
			else
			{
				res.add(file);
			}
		}
		
		return res;
	}

	/**
	 * @param arguments Liste des chemins d'accès vers les fichiers à étudier
	 */
	public static void main(String[] arguments)
	{
		boolean showRequested = false;
		
		LinkedList<File> files = new LinkedList<File>();
		
		for(String path : arguments)
		{
			if(path.equals("--show"))
			{
				showRequested = true;
			}
			else
			{
				File file = new File(path);
				
				if(!file.exists())
				{
					throw new IllegalArgumentException("Le fichier "+path+" n'existe pas.");
				}
				
				if(file.isDirectory())
				{
					files.addAll(filesInDirectory(file));
				}
				else
				{
					files.add(file);
				}
			}
		}
		
		int nbFiles = files.size();
		
		Document[] documents = new Document[nbFiles];
		
		{
			int i=0;
			for(File file : files)
			{
				documents[i] = new Document(file);
				i++;
			}
		}
		
		ComparisonResult[] results = new ComparisonResult[(nbFiles*(nbFiles-1))/2];
		
		for(int i=0 ; i<nbFiles-1 ; i++)
		{
			for(int j=i+1 ; j<nbFiles ; j++)
			{
				Document doc1 = documents[i];
				Document doc2 = documents[j];
				results[i*(nbFiles-1)+j] = doc1.compareWith(doc2);
			}
		}
		
		Arrays.sort(results, new ResultComparator());
		
		for(ComparisonResult result : results)
		{
			if(result.nbOfSharedFingerprints()>0)
			{
				result.display(showRequested);
			}
		}
	}

}
