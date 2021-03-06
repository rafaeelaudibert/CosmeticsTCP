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

public class EvaluationTest {

	User userJoao, userJose;
	Category cream;
	List<Category> categoryListCream;
	Product creamProduct;
	Group groupA;
	
	// Static values
	static Integer LOWER_LIMIT = -3;
	static Integer UPPER_LIMIT = 3;

	@Before
	public void setUp() throws BusinessException {
		// Definindo categorias para teste
		cream = new Category("creme");
		categoryListCream = new ArrayList<Category>();
		categoryListCream.add(cream);

		// Definindo Grupo
		groupA = new Group("Grupo A");

		// Definindo Usuario
		userJose = new User(02, "Jose", "CE", categoryListCream);
		userJoao = new User(01, "Joao", "RS", categoryListCream);

		// Definindo Produto
		creamProduct = new Product(01, "Creme X", userJose, cream, groupA);

		groupA.addProduct(creamProduct);
		groupA.addMember(userJoao);
		userJoao.addGroup(groupA);
	}

	// Test isDone
	@Test
	public void isDoneTrueTest() throws BusinessException {
		Evaluation evaluation = new Evaluation(userJoao, creamProduct);

		evaluation.setScore(1);
		assertTrue(evaluation.isDone());
	}

	@Test
	public void isDoneFalseTest() throws BusinessException {
		Evaluation evaluation = new Evaluation(userJoao, creamProduct);

		assertFalse(evaluation.isDone());
	}
	
	@Test(expected = BusinessException.class)
	public void isDoneFalseWithExceptionTest() throws BusinessException {
		Evaluation evaluation = new Evaluation(userJoao, creamProduct);
		
		try {
			evaluation.setScore(-5);
		} catch (BusinessException e) {
			assertFalse(evaluation.isDone());
			throw e;
		}
	}

	// Test setScore
	@Test
	public void setScoreTest() throws BusinessException {
		Evaluation evaluation = new Evaluation(userJoao, creamProduct);

		evaluation.setScore(0);
		assertTrue(evaluation.getScore() == 0);
	}
	
	@Test(expected = BusinessException.class)
	public void setScoreSmallerValueTest() throws BusinessException {
		Evaluation evaluation = new Evaluation(userJoao, creamProduct);

		evaluation.setScore(LOWER_LIMIT - 1);
	}

	@Test
	public void setScoreLowerLimitTest() throws BusinessException {
		Evaluation evaluation = new Evaluation(userJoao, creamProduct);

		evaluation.setScore(LOWER_LIMIT);
		assertTrue(evaluation.getScore() == LOWER_LIMIT);
	}

	@Test
	public void setScoreHigherLimitTest() throws BusinessException {
		Evaluation evaluation = new Evaluation(userJoao, creamProduct);

		evaluation.setScore(UPPER_LIMIT);
		assertTrue(evaluation.getScore() == UPPER_LIMIT);
	}
	
	@Test(expected = BusinessException.class)
	public void setScoreHigherValueTest() throws BusinessException {
		Evaluation evaluation = new Evaluation(userJoao, creamProduct);

		evaluation.setScore(UPPER_LIMIT + 1);
	}	

	@Test(expected = BusinessException.class)
	public void setScoreAfterSetTest() throws BusinessException {
		Evaluation evaluation = new Evaluation(userJoao, creamProduct);

		evaluation.setScore(0);
		evaluation.setScore(-5);
	}

	@Test(expected = BusinessException.class)
	public void setScoreNullTest() throws BusinessException {
		Evaluation evaluation = new Evaluation(userJoao, creamProduct);

		evaluation.setScore(null);
	}

}
