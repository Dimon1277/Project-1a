package edu.hccs.dmitriy;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
@Scope("prototype")
public class StudentController {


    /***
     * Read the student.txt file
     * @return all the students
     * @throws JsonMappingException 
     * @throws JsonParseException 
     * @throws IOException
     */
    public List<Student> runAssignmentProject1() throws JsonParseException, JsonMappingException, IOException{
    	List<Student> students;
		ObjectMapper mapper = new ObjectMapper();
//		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		InputStream input = new URL("https://hccs-advancejava.s3.amazonaws.com/student_course.json").openStream();
		students = mapper.readValue(input, new TypeReference<List<Student>>() {
		});
		System.out.println("Loaded students " + students.size());
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter a name: ");
		String name = sc.next();
		System.out.print("Enter a course: ");
		String no = sc.next();
		System.out.println("You have entered: " + name + " " + no);
		search(students, name, no);
		return students;
    }

	private Course search(List<Student> students, String name, String course_no) {
		for (Student s : students) {
			if (s.firstName.equalsIgnoreCase(name))
			{
				System.out.println(s.firstName+"'s GPA="+s.getGPA());
				if (s.course != null)
					for (Course c : s.course) {
						if (c.courseNo.equals(course_no)) {
							System.out.println("I found " + s.firstName + " " + c.courseNo);
							return c;
						}
					}
			}
		}
		System.out.println("I have not found" + name + " " + course_no);
		return null;
		}
}