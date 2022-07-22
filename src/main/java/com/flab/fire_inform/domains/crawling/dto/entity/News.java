package com.flab.fire_inform.domains.crawling.dto.entity;

import lombok.Getter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;
import java.time.LocalDateTime;

@Getter
@Alias("news")
@ToString
public class News {
        private Long id;
		private  String site;
		private  String domain;
		private  String title;
		private String description;
		private String imageUrl;
		private  String link;
		private String newsTotalUrl;
		private LocalDateTime upload_at;
		private LocalDateTime update_at;

		private News(NewsBuilder newsBuilder){
			this.id = newsBuilder.id;
			this.site = newsBuilder.site;
			this.domain = newsBuilder.domain;
			this.title = newsBuilder.title;
			this.description = newsBuilder.description;
			this.imageUrl = newsBuilder.imageUrl;
			this.link = newsBuilder.link;
			this.newsTotalUrl = newsBuilder.newsTotalUrl;
			this.upload_at = newsBuilder.upload_at;
			this.update_at = newsBuilder.update_at;
		}


		public News(){};

		public static NewsBuilder builder(String site, String domain, String title, String link, String newsTotalUrl){
			return new NewsBuilder(site, domain, title, link, newsTotalUrl);
		}

		public static class NewsBuilder{
			private Long id = 0L;
			private final String site;
			private final String domain;
			private final String title;
			private String description;
			private String imageUrl;
			private final String link;
			private final String newsTotalUrl;
			private LocalDateTime upload_at;
			private LocalDateTime update_at;
		public NewsBuilder(String site, String domain, String title, String link, String newsTotalUrl){
			this.site = site;
			this.domain = domain;
			this.title = title;
			this.link = link;
			this.newsTotalUrl = newsTotalUrl;
		}

		public NewsBuilder description(String description){
			this.description = description;
			return this;
		}

		public News build(){
			return new News(this);
		}

		}

}
