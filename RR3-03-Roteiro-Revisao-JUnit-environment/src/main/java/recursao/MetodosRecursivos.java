package recursao;

public class MetodosRecursivos {

	public int calcularSomaArray(int[] array){
		int result = 0;
		// TODO ESCREVA AQUI O CÓDIGO (USANDO RECURSAO) PARA CALCULAR A SOMA
		// DOS EMENTOS DE UM ARRAY
		return result;
	}
	public long calcularFatorial(int n) {
		long result = 1;
		if (n==0){
			result = 1;
		}else{
			return n*calcularFatorial(n-1);
		}
		//pilha
		System.out.println(n + "! = " + result);
		
		return result;
	}

	public int calcularFibonacci(int n) {
		int result = 1;
		// TODO ESCREVA AQUI O CÓDIGO (USANDO RECURSAO) PARA CALCULAR E IMPRIMIR
		// O N-ESIMO TERMO
		// DA SEQUENCIA DE FIBONACCI, QUE TEM A SEGUINTE LEI DE FORMACAO: O
		// PRIMEIRO E SEGUNDO NUMEROS
		// DA SEQUENCIA SÃO 1. A PARTIR DO TERCEIRO TERMO, CADA TERMO DA
		// SEQUENCIA É DADO
		// PELA SOMA DOS OUTROS DOIS ANTERIORES. OBSERVE QUE SENDO O METODO
		// RECURSIVO, O FIBONACCI DOS NUMEROS ANTERIORES TAMBEM VAO SER
		// IMPRESSOS
		return result;
	}

	public int countNotNull(Object[] array) {
		int result = 0;
		result = countNotNull(array, 0);
		return result;
	}
	
	public int countNotNull(Object[] array, int indice){
		int result = 0;
		if (indice == array.length-1){
			if (array[indice] != null){
				result = 1;
			}
		}else{
			result = result + countNotNull(array, indice+1);
			
		}
		return result;
	}

	public long potenciaDe2(int expoente) {
		if (expoente == 0){
			return 1;
		}
		return  2 * potenciaDe2(expoente - 1);
		
	}

	public double progressaoAritmetica(double termoInicial, double razao, int n) {
		double result = termoInicial;
		if (n==1){
			result = termoInicial;
		}else{
			result = razao + progressaoAritmetica(termoInicial, razao, n-1);
		}
		return result;
	}

	public static double progressaoGeometrica(double termoInicial, double razao, int n) {
		double result = termoInicial;
		if (n==1){
			result = termoInicial;
		}else{
			result =progressaoGeometrica(termoInicial, razao, n-1) +razao;
		}
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(progressaoGeometrica(1,2,2));
	}
	
}
