package com.substring.quiz;

import com.substring.quiz.dtos.CategoryDto;
import com.substring.quiz.service.CategoryFeignService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class QuizServiceApplicationTests {

	@Autowired
	private CategoryFeignService categoryFeignService;

	@Test
	public void testFeignAllCategories() {

		System.out.println("Getting all categories");
		List<CategoryDto> all = categoryFeignService.findAll();
		all.forEach(categoryDto -> System.out.println(categoryDto.getTitle()));

//        Assertions.assertEquals(4, all.size()); //expected output and actual output if match then test case pass
		Assertions.assertNotNull(all); //to check its null or not if all then test case fail otherwise pass
	}


	@Test
	public void testFeignSingleCategory() {

		System.out.println("Getting single category");
		CategoryDto categoryDto = categoryFeignService.findById("cf7affb7-dc9b-4f02-9c93-81cccb1c8a5c");
		System.out.println(categoryDto.getTitle());
		Assertions.assertNotNull(categoryDto);
	}

}
