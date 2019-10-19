package com.reviews;

import com.mongodb.MongoClient;

import com.reviews.config.TestConfig;
import com.reviews.model.Comment;
import com.reviews.model.Product;
import com.reviews.model.Review;
import com.reviews.repository.jpa.CommentRepository;
import com.reviews.repository.jpa.ProductRepository;
import com.reviews.repository.jpa.ReviewRepository;
import com.reviews.repository.mongo.ReviewMongoRepository;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@ExtendWith(SpringExtension.class)
@EntityScan("com.reviews.model")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
public class ReviewsApplicationTests {

	private MongodExecutable mongodExecutable;

	private MongoTemplate mongoTemplate;

	@AfterEach
	void clean() {
		mongodExecutable.stop();
	}

	@BeforeEach
	public void setup() throws Exception {
		String ip = "localhost";
		int port = 27001;

		IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
				.net(new Net(ip, port, Network.localhostIsIPv6()))
				.build();

		MongodStarter starter = MongodStarter.getDefaultInstance();
		mongodExecutable = starter.prepare(mongodConfig);
		mongodExecutable.start();
		mongoTemplate = new MongoTemplate(new MongoClient(ip, port), "test1");
	}



	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ReviewMongoRepository reviewMongoRepository;

	@BeforeEach
	public void testSetUp(){
		try {
			long l=1l;
				Product product = new Product();
				product.setId(l);
				product.setName("apple" + l);
				product.setDescription("notebook" + l);
				productRepository.save(product);

				Review review = new Review();
				review.setId(l);
				review.setProduct(productRepository.findById(l).get());
				review.setTitle("reviews" + l);
				review.setText("reviewtext" + l);
				reviewRepository.save(review);
				reviewMongoRepository.save(review);

				Comment comment = new Comment();
				comment.setId(l);
				comment.setText("comments" + l);
				comment.setTitle("comment tiemte" + l);
				comment.setReview(reviewRepository.findById(l).get());
				commentRepository.save(comment);
				review.addComment(comment);
				reviewMongoRepository.save(review);

		}catch(NoSuchElementException e){
		e.printStackTrace();
		}
	}



	@Test
	public void test01CommentRepoSize() throws Exception {
		List<Comment> comments= commentRepository.findAll();
		Assertions.assertEquals(7, comments.size());
	}

	@Test
	public void test02ReviewRepoSize() throws Exception {
		List<Review> reviews= reviewRepository.findAll();
		Assertions.assertEquals(5, reviews.size());
	}

	@Test
	public void test03ProductRepoSize() throws Exception {
		List<Product> products= productRepository.findAll();
		Assertions.assertEquals(3, products.size());
	}

	@Test
	public void test04ReviewFindById(){
		Review review= reviewRepository.findById(1l).get();
		Assertions.assertEquals("reviewtext"+1l,review.getText());
	}

	@Test
	public void test05FindAllReviewByProduct(){
		List<Review> reviews= reviewRepository.findAllByProduct(productRepository.findById(1l).get());
		Assertions.assertEquals(3, reviews.size());
	}

	@Test
	public void test06FindAllCommentByReview(){
		List<Comment> comments= commentRepository.findAllByReview(reviewRepository.findById(1l).get());
		Assertions.assertEquals("comments" + 1l, comments.get(0).getText());
		Assertions.assertEquals("comment tiemte" + 1l, comments.get(0).getTitle());
	}


	@Test
	public void test07ReviewByMongoId(){
		Review review= reviewMongoRepository.findById(1l).get();
		Assertions.assertEquals("reviewtext"+1l,review.getText());
	}


	@Test
	public void test08GetCommentsFromMongo(){
		Review review= reviewMongoRepository.findById(1l).get();
		System.out.println(review.getCommentList().size());
	}



}