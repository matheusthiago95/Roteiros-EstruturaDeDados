package vetor;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import vetor.Aluno;
import vetor.Vetor;

public class TesteVetor {
	
	private Vetor<Aluno> vetorCheio;
	private Vetor<Aluno> vetorVazio;

	@Before
	public void setUp() throws Exception {
		vetorVazio = new Vetor<Aluno>(1);
		vetorCheio = new Vetor<Aluno>(5);
		
		
	}

	@Test
	public void testInserir() {
		Assert.assertTrue(vetorCheio.isVazio());
		vetorCheio.inserir(new Aluno("Pedro", 10));
		Assert.assertFalse(vetorCheio.isVazio());
	}

	@Test
	public void testRemover() {
		Assert.assertTrue(vetorCheio.isVazio());
		vetorCheio.inserir(new Aluno("Pedro", 10));
		Assert.assertFalse(vetorCheio.isVazio());
		vetorCheio.remover(new Aluno("Pedro", 10));
		Assert.assertTrue(vetorCheio.isVazio());
	}

	@Test
	public void testProcurar() {
		Assert.assertTrue(vetorCheio.isVazio());
		vetorCheio.inserir(new Aluno("Pedro", 10));
		Assert.assertFalse(vetorCheio.isVazio());
		Assert.assertNotNull(vetorCheio.procurar(new Aluno("Pedro", 10)));
	}

	@Test
	public void testIsVazio() {
		Assert.assertTrue(vetorCheio.isVazio());
		vetorCheio.inserir(new Aluno("Pedro", 10));
		Assert.assertFalse(vetorCheio.isVazio());
		vetorCheio.remover(new Aluno("Pedro", 10));
		Assert.assertTrue(vetorCheio.isVazio());
	}

	@Test
	public void testIsCheio() {
		Assert.assertFalse(vetorVazio.isCheio());
		vetorVazio.inserir(new Aluno("Pedro", 10));
		Assert.assertTrue(vetorVazio.isCheio());
		
	}

}
