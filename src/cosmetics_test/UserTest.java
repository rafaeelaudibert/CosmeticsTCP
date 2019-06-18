package cosmetics_test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cosmetics.*;

public class UserTest {

	User userJoao, userJose, userPaulo, userMateus;
	List<User> groupAMembers, groupBMembers;
	Category cream, lotion, shampoo;
	List<Category> categoryListEmpty, categoryListCream, categoryListCreamShampoo;
	Product creamProduct, lotionProduct, shampooProduct;
	List<Product> productListEmpty, productListShampoo, productListCreamLotion;
	Group groupA, groupB;
	Evaluation evalJoaoCreamProduct, evalMateusCreamProduct;
	Evaluation evalJoaoCreamProductWrongGroup, evalJoaoShampooProduct;

	@Before
	public void setUp() {
		// Definindo categorias para teste
		cream = new Category("creme");
		lotion = new Category("locao");
		shampoo = new Category("shampoo");

		// Definindo listas de categorias para teste
		categoryListEmpty = new ArrayList<Category>();
		categoryListCream = new ArrayList<Category>(); // Cria outra lista de categorias
		categoryListCream.add(cream);
		categoryListCreamShampoo = new ArrayList<Category>();
		categoryListCreamShampoo.add(cream);
		categoryListCreamShampoo.add(shampoo);
		
		// Definindo usu�rios para teste
		userJoao = new User(01, "Joao", "RS", categoryListCream);
		userMateus = new User(02, "Mateus", "BA", categoryListCream);
		userJose = new User(03, "Jose", "RS", categoryListCreamShampoo);
		userPaulo = new User(04, "Paulo", "MG", categoryListEmpty);

		// Definindo produtos para teste
		creamProduct = new Product(01, "Creme X", userPaulo, cream, groupB);
		lotionProduct = new Product(02, "Locao Y", userJose, lotion, null);
		shampooProduct = new Product(03, "Shampoo Z", userJoao, shampoo, groupB);

		// Definindo listas de produtos para teste;
		productListEmpty = new ArrayList<Product>();
		productListShampoo = new ArrayList<Product>();
		productListShampoo.add(shampooProduct);
		productListCreamLotion = new ArrayList<Product>();
		productListCreamLotion.add(creamProduct);
		productListCreamLotion.add(lotionProduct);

		// Definindo listas de usu�rios para teste
		groupAMembers = new ArrayList<User>();
		groupAMembers.add(userJose);
		groupAMembers.add(userPaulo);
		groupBMembers = new ArrayList<User>();
		groupBMembers.add(userJoao);
		groupBMembers.add(userMateus);

		// Definindo os grupos de usuarios
		groupA = new Group("Grupo A", productListShampoo, groupAMembers);
		groupB = new Group("Grupo B", productListCreamLotion, groupBMembers);

		// Definindo novas avalia��es e avalia��es concluidas;
	}

	// Testando canEvaluate
	@Test
	public void canEvaluateTestInList() {
		assertTrue(userJoao.canEvaluate(creamProduct));
	}

	@Test
	public void canEvaluateTestNotInList() {
		assertFalse(userJose.canEvaluate(lotionProduct));
	}

	@Test
	public void canEvaluateTestNullProduct() {
		assertFalse(userJose.canEvaluate(null));
	}
	
	@Test
	public void canEvaluateTestSameState() {
		assertFalse(userJose.canEvaluate(shampooProduct));
	}

	@Test
	public void canEvaluateTestEmptyList() {
		assertFalse(userPaulo.canEvaluate(shampooProduct));
	}

	// Testando addEvaluationGroup
	@Test(expected=Exception.class)
	public void addGroupNotInGroupTest() throws Exception {
		// UserJoao já deve estar no grupo A
		userJoao.addGroup(groupA);
	}

	@Test(expected=Exception.class)
	public void addGroupTestNullAddition() throws Exception {
		userJoao.addGroup(null);
	}

	@Test
	public void addGroupTestRepeatAddition() throws Exception {
		userJoao.addGroup(groupB);
		assertTrue(userJoao.getGroups().contains(groupB));
	}

	// Testando addEvaluation
	@Test(expected=NullPointerException.class)
	public void addEvaluationTestNull() {
		userJoao.addEvaluation(null);
	}

	@Test
	public void addEvaluationTestWrongUser() {
		evalMateusCreamProduct = new Evaluation(userMateus, creamProduct, groupB);
		userJoao.addEvaluation(evalMateusCreamProduct);
		assertFalse(userJoao.getEvaluationsFromGroup(evalMateusCreamProduct.getGroup()).contains(evalMateusCreamProduct));
	}

	@Test
	public void addEvaluationTestIncompatibleProduct() {
		evalJoaoShampooProduct = new Evaluation(userJoao, shampooProduct, groupB);
		userJoao.addEvaluation(evalJoaoShampooProduct);
		assertFalse(userJoao.getEvaluationsFromGroup(evalJoaoShampooProduct.getGroup()).contains(evalJoaoShampooProduct));
	}

	@Test
	public void addEvaluationTestNotExistingGroup() {
		evalJoaoCreamProductWrongGroup = new Evaluation(userJoao, creamProduct, groupA);
		userJoao.addEvaluation(evalJoaoCreamProductWrongGroup);
		assertTrue(userJoao.getEvaluationsFromGroup(evalJoaoCreamProductWrongGroup.getGroup()) == null);
	}

	@Test
	public void addEvaluationTestNormalAddition() {
		evalJoaoCreamProduct = new Evaluation(userJoao, creamProduct, groupB);
		userJoao.addEvaluation(evalJoaoCreamProduct);
		assertTrue(userJoao.getEvaluationsFromGroup(evalJoaoCreamProduct.getGroup()).contains(evalJoaoCreamProduct));
	}
}
