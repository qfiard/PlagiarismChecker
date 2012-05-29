import java.io.*;

import java.util.*;

import structures.*;
import utilities.*;
import normalization.*;

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
		System.out.println("Projet de programmation INF431");
		System.out.println("Algorithme de détection de plagiats");
		System.out.println();
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
					throw new IllegalArgumentException("Le fichier "+file.getAbsolutePath()+" n'existe pas.");
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
				documents[i] = new Document(file,new TextNormalizer());
				i++;
			}
		}
		
		int nbComparaisons = (nbFiles*(nbFiles-1))/2;
		
		ComparisonResult[] results = new ComparisonResult[nbComparaisons];
		
		double avancement = 0;
		int pourcentage = 0;
		
		for(int i=0 ; i<nbFiles ; i++)
		{
			for(int j=0 ; j<i ; j++)
			{
				avancement += 100./nbComparaisons;
				
				//if(avancement>=pourcentage+1)
				{
					pourcentage = (int)Math.round(avancement);
					//System.out.println("Avancement : "+pourcentage+"%");
					System.out.println("Avancement : "+avancement+"%");
				}
				
				Document doc1 = documents[i];
				Document doc2 = documents[j];
				results[i*(i-1)/2+j] = doc1.compareWith(doc2);
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
