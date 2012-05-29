public class Ajout{

	public static void main (String[] args){
		for(int i=1;i<=50;i++){
	//		String requete = "insert into DOUANE(ID_DOU,MAIL,ID_PAY,MDP) values ("+i+",'douane"+i+"@douane.fr',"+i+",'11111111');";
				System.out.println(Remplissage.nomAleatoire()+"    "+Remplissage.alea(50,200));
		}
	}
}