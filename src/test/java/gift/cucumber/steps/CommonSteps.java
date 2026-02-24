package gift.cucumber.steps;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;

import gift.cucumber.TestContext;
import gift.model.Category;
import gift.model.CategoryRepository;
import gift.model.Member;
import gift.model.MemberRepository;
import gift.model.Option;
import gift.model.OptionRepository;
import gift.model.Product;
import gift.model.ProductRepository;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

public class CommonSteps {

    @Autowired
    private TestContext context;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @After
    public void cleanup() {
        optionRepository.deleteAll();
        productRepository.deleteAll();
        categoryRepository.deleteAll();
        memberRepository.deleteAll();
    }

    // === 공통 Given Steps ===

    @Given("카테고리 {string}가 존재한다")
    public void 카테고리가_존재한다(String name) {
        Category category = categoryRepository.save(new Category(name));
        context.setCategory(category);
    }

    @Given("상품 {string}이 존재한다")
    public void 상품이_존재한다(String name) {
        Product product = productRepository.save(
                new Product(name, 1000, "https://test.com/image.jpg", context.getCategory()));
        context.setProduct(product);
    }

    @Given("보내는 사람 {string}이 존재한다")
    public void 보내는_사람이_존재한다(String name) {
        Member sender = memberRepository.save(new Member(name, name + "@test.com"));
        context.setSender(sender);
    }

    @Given("받는 사람 {string}이 존재한다")
    public void 받는_사람이_존재한다(String name) {
        Member receiver = memberRepository.save(new Member(name, name + "@test.com"));
        context.setReceiver(receiver);
    }

    @Given("재고가 {int}개인 옵션 {string}이 존재한다")
    public void 옵션이_존재한다(int quantity, String name) {
        Option option = optionRepository.save(new Option(name, quantity, context.getProduct()));
        context.setOption(option);
    }

    // === 공통 Then Steps ===

    @Then("응답 상태 코드는 {int}이다")
    public void 응답_상태_코드는(int statusCode) {
        context.getLastResponse().then().statusCode(statusCode);
    }

    @Then("목록에 1개 이상의 상품이 있다")
    public void 목록에_1개_이상의_상품이_있다() {
        context.getLastResponse().then().body("size()", greaterThanOrEqualTo(1));
    }

    @Then("목록에 1개 이상의 카테고리가 있다")
    public void 목록에_1개_이상의_카테고리가_있다() {
        context.getLastResponse().then().body("size()", greaterThanOrEqualTo(1));
    }

    @Then("목록에 {string}가 포함되어 있다")
    public void 목록에_이름이_포함되어_있다(String name) {
        context.getLastResponse().then().body("name", hasItem(name));
    }
}
