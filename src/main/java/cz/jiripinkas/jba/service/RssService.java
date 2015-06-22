package cz.jiripinkas.jba.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.springframework.stereotype.Service;

import cz.jiripinkas.jba.entity.Item;
import cz.jiripinkas.jba.exception.RssException;
import cz.jiripinkas.jba.rss.ObjectFactory;
import cz.jiripinkas.jba.rss.TRss;
import cz.jiripinkas.jba.rss.TRssChannel;
import cz.jiripinkas.jba.rss.TRssItem;

@Service
public class RssService {
	
	public List<Item> getItems(String url) throws RssException{
		return getItems(new StreamSource(url));
	}
	

	private List<Item> getItems(Source source) throws RssException{
		List<Item> list = new ArrayList<Item>();
		try {
			JAXBContext jAXBContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller unmarshaller = jAXBContext.createUnmarshaller();
			JAXBElement<TRss> jaxbElement = unmarshaller.unmarshal(source, TRss.class);  
			TRss rss = jaxbElement.getValue();
			
			List<TRssChannel> rssChannels = rss.getChannel();  
			for(TRssChannel chanel : rssChannels){
				List<TRssItem> items = chanel.getItem();
				for(TRssItem rssItem : items){
					Item item = new Item();
					item.setTitle(rssItem.getTitle());
					item.setDescription(rssItem.getDescription());
					item.setLink(rssItem.getLink());
					java.util.TimeZone T1 = TimeZone.getTimeZone("GMT");
					SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
					dateFormat.setTimeZone(T1);
					Date pusDate =  dateFormat.parse(rssItem.getPubDate());
					item.setPublishedDate(pusDate);
					list.add(item);
				}
				
			}
		} catch (JAXBException | ParseException e) {
			throw new RssException(e);
		}
		return list;
	}
}
