package cz.jiripinkas.jba.annotation;

import java.io.IOException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class RssUrlValidator implements ConstraintValidator<RssUrl, String> {

	@Override
	public void initialize(RssUrl arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(String url, ConstraintValidatorContext arg1) {
		DocumentBuilder builder = null;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException | FactoryConfigurationError e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Document doc = null;
		try {
			doc = builder.parse(url);
		} catch (SAXException e) {
			return false;
		} catch (IOException e) {
			return false;
		} // url is your url for testing
		return doc.getDocumentElement().getNodeName().equalsIgnoreCase("rss");
	}

}
