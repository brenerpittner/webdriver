package testsFront;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.NoSuchElementException;

public class PageHome {
	private WebDriver driver;
	
	private List<WebElement> txtArticle = new ArrayList<WebElement>();
	private List<String> cursos = new ArrayList<String>();
	
	@SuppressWarnings("deprecation")

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", System.getenv("Driver"));
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://www.google.com/");
		//driver.get("https://www.sp.senai.br/cursos/cursos-livres/tecnologia-da-informacao-e-informatica?pag=1");
		currentUrl();
	}

	@Test
	public void acessarSite() {
		//Procura e acessar site do senai
		driver.findElement(By.className("gLFyf")).sendKeys("Senai");
		driver.findElement(By.className("gLFyf")).sendKeys(Keys.ENTER);
		driver.findElement(By.className("LC20lb")).click();
		currentUrl();

		driver.findElement(By.id("dropdown-cursos")).click();
		String categoria = "Tecnologia da Informação e Informática";
		//String categoria = "Pesquisar Todos"; //se usar isso, comentar linha 45 e 48
		driver.findElement(By.linkText(categoria)).click();	
		driver.findElement(By.linkText("Cursos Livres")).click();
		currentUrl();
		

		while(findElements("card-title") == true) {
			findElements("card-title");
			setElement();			
			if (findNextPage() == true)
				goNextPage();
			else {
				break;
			}
		}

		System.out.println(cursos);
		System.out.println("Categoria: " + categoria);
		System.out.println("Quantidade de cursos: " + cursos.size());
		
	}
	private String currentUrl() {
		String current_url = driver.getCurrentUrl();
		System.out.println(current_url);
		return current_url;
	}
	
	private void setElement() {
		int i = 0;
		for (WebElement title : txtArticle) {
			if(i==0) { //pular o primeiro elemento que vem vazio
				i=1;
				continue;
			}else {
				cursos.add(title.getText());
				System.out.println(title.getText());
			}
        }
	}
	
	private boolean findElements(String element) {
		try {
			txtArticle = driver.findElements(By.className(element));
			return true;
		}catch(NoSuchElementException  e) {
			return false;
		}
	}
	
	private void goNextPage() {
		driver.findElement(By.xpath("//button[text()='Próximo']")).click();
	}
	
	private boolean findNextPage() {
		try {
			driver.findElement(By.xpath("//button[text()='Próximo']"));
			return true;
		}catch(NoSuchElementException  e){
			return false;
		}
	}	
	
	
	@After
	public void fechar() {	
		driver.close();
	}
	
}
