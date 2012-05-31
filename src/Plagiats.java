import java.io.*;

import java.util.*;

import streams.FingerprintStream;
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
		
		boolean kgramSizeWasChanged = false;
		boolean thresholdWasChanged = false;
		
		LinkedList<File> files = new LinkedList<File>();
		
		for(String path : arguments)
		{
			if(path.equals("--show"))
			{
				showRequested = true;
			}
			else if(path.equals("--help"))
			{
				System.out.println("Notice d'utilisation du programe :");
				System.out.println();
				System.out.println("Utilisation : java Plagiats cmd1 cmd2 ... fichierOuRepertoire1 fichierOuRepertoire2 ...");
				System.out.println();
				System.out.println("où cmd1,cmd2 ... sont issues de la liste suivante :");
				System.out.println();
				System.out.println("\t--kgram=...  : choix de la taille des k-grammes.");
				System.out.println("\t--threshold=...  : choix du seuil de détection, qui correspond au plus petit plagiat que l'on souhaite être sûr de détecter.");
				System.out.println("\t--show  : affichage des informations de position et des extraits correspondants aux empreintes communes.");
				System.out.println();
				System.out.println("et fichierOuRepertoire1, fichierOuRepertoire2 ... sont des chemins d'accès vers des fichiers ou des répertoires, absolus, ou relatifs au répertoire du projet ("+(new File(".")).getAbsolutePath()+")");
				System.out.println();
				System.exit(0);
			}
			else if(path.contains("--kgram="))
			{
				path = path.replace("--kgram=", "");
				
				System.out.println("La taille des k-grammes a été fixée à "+path);
				
				Kgram.kgramSize = Integer.parseInt(path);
				
				kgramSizeWasChanged = true;
			}
			else if(path.contains("--threshold="))
			{
				path = path.replace("--threshold=", "");
				
				System.out.println("La taille du seuil a été fixée à "+path);
				
				FingerprintStream.guarantyThreshold = Integer.parseInt(path);
				
				thresholdWasChanged = true;
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
		
		if(!kgramSizeWasChanged)
		{
			System.out.println("Taille des k-grammes par défault : "+Kgram.kgramSize);
		}
		if(!thresholdWasChanged)
		{
			System.out.println("Taille du seuil par défault "+FingerprintStream.guarantyThreshold);
		}
		
		if(Kgram.kgramSize>FingerprintStream.guarantyThreshold)
		{
			throw new IllegalArgumentException("Le seuil doit être supérieur à la taille des k-grammes");
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
		double pourcentage = 0;
		
		for(int i=0 ; i<nbFiles ; i++)
		{
			for(int j=0 ; j<i ; j++)
			{
				avancement = 100*((double)((i*(i-1)/2+j+1)))/nbComparaisons;
				
				if(avancement>=pourcentage+0.1)
				{
					pourcentage = Math.floor(10*avancement)/10;
					System.out.println("Avancement : "+pourcentage+" %");
					//System.out.println("Avancement : "+avancement+"%");
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
