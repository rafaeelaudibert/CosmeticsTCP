package cosmetics.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cosmetics.business.BusinessException;
import cosmetics.business.Category;
import cosmetics.business.Evaluation;
import cosmetics.business.Group;
import cosmetics.business.Product;
import cosmetics.business.User;

public class UserTest {

	User userJoao, userJose, userPaulo, userMateus;
	Category cream, lotion, shampoo;
	List<Category> categoryListEmpty, categoryListCream, categoryListCreamShampoo;
	Product creamProduct, lotionProduct, shampooProduct, soapProduct;
	Group groupA, groupB, groupC;
	Evaluation evalMateusCreamProduct, evalJoaoShampooProduct, evalJoseCreamProduct;

	@Before
	public void setUp() throws BusinessException {
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

		// Definindo usuários para teste
		userJoao = new User(01, "Joao", "RS", categoryListCream);
		userMateus = new User(02, "Mateus", "BA", categoryListCream);
		userJose = new User(03, "Jose", "RS", categoryListCreamShampoo);
		userPaulo = new User(04, "Paulo", "MG", categoryListEmpty);

		// Definindo os grupos e inicializando os usuários contidos no mesmo
		groupA = new Group("Grupo A");
		groupB = new Group("Grupo B");
		groupC = new Group("Grupo C");

		groupA.addMember(userJose);
		groupA.addMember(userPaulo);
		groupA.addMember(userJoao);
		userJoao.addGroup(groupA);
		userJose.addGroup(groupA);
		userPaulo.addGroup(groupA);

		groupB.addMember(userJoao);
		groupB.addMember(userMateus);
		userJoao.addGroup(groupB);
		userMateus.addGroup(groupB);

		// Definindo produtos para teste
		creamProduct = new Product(01, "Creme X", userPaulo, cream, groupA);
		lotionProduct = new Product(02, "Locao Y", userJose, lotion, groupB);
		shampooProduct = new Product(03, "Shampoo Z", userMateus, shampoo, groupB);
		soapProduct = new Product(04, "Sabonete W", userJoao, lotion, groupB);

		// Definindo os produtos por grupo
		groupA.addProduct(creamProduct);
		groupB.addProduct(lotionProduct);
		groupB.addProduct(shampooProduct);

	}

	// Test canEvaluate
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
		assertFalse(userJose.canEvaluate(soapProduct));
	}

	@Test
	public void canEvaluateTestEmptyList() {
		assertFalse(userPaulo.canEvaluate(shampooProduct));
	}

	// Test addEvaluationGroup
	@Test(expected = BusinessException.class)
	public void addGroupNotInGroupTest() throws BusinessException {
		userJoao.addGroup(groupC); // UserJoao is already in group C
	}

	@Test(expected = BusinessException.class)
	public void addGroupTestNullAddition() throws BusinessException {
		userJoao.addGroup(null);
	}

	@Test
	public void addGroupTestRepeatAddition() throws BusinessException {
		userJoao.addGroup(groupB);
		assertTrue(userJoao.getGroups().contains(groupB));
	}

	// Test addEvaluation
	@Test(expected = BusinessException.class)
	public void addEvaluationTestNull() throws BusinessException {
		userJoao.addEvaluation(null);
	}

	@Test(expected = BusinessException.class)
	public void addEvaluationTestWrongUser() throws BusinessException {
		evalMateusCreamProduct = new Evaluation(userMateus, creamProduct);
		userJoao.addEvaluation(evalMateusCreamProduct);
	}

	@Test(expected = BusinessException.class)
	public void addEvaluationTestIncompatibleProduct() throws BusinessException {
		evalJoaoShampooProduct = new Evaluation(userJoao, shampooProduct);
		userJoao.addEvaluation(evalJoaoShampooProduct);
	}

	@Test(expected = BusinessException.class)
	public void addEvaluationTestNotExistingGroup() throws BusinessException {
		evalMateusCreamProduct = new Evaluation(userMateus, creamProduct);
		userMateus.addEvaluation(evalMateusCreamProduct);
	}

	@Test
	public void addEvaluationTestNormalAddition() throws BusinessException {
		evalJoseCreamProduct = new Evaluation(userJose, creamProduct);
		userJose.addEvaluation(evalJoseCreamProduct);
		assertTrue(userJose.getEvaluationsFromGroup(evalJoseCreamProduct.getGroup()).contains(evalJoseCreamProduct));
	}
}
