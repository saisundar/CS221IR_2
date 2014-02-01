/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.uci.ics.crawler4j.examples.basic;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * @author Yasser Ganjisaffar <lastname at gmail dot com>
 */
public class BasicCrawlController {

		public static HashSet<String> stopWords = new HashSet<String>();
		public static void readStopWords(){
			
			BufferedReader inputStream = null;
			try
			{
				inputStream = new BufferedReader(new FileReader("C:\\Users\\SAISUNDAR\\Documents\\WINTER 2014 COURSES\\Winter Github repositories\\CS221IR_2\\stopWords.txt"));
				String l;
				boolean isEmpty=true;
				while ((l = inputStream.readLine()) != null) {
					if(l.length()==0)continue;
					stopWords.add(l);
					System.out.print(l+'\n');
				}
			}
			catch (FileNotFoundException e) {
				System.err.println("FileNotFoundException: " + e.getMessage());

			}
			catch (IOException e) {
				System.err.println("Caught IOException: " + e.getMessage());
			}
			finally {
				try
				{
					if (inputStream != null) {
						inputStream.close();
					}
				}
				catch (IOException e) {
					System.err.println("IO exception trying to close the stream " + e.getMessage());
				}

			}
		
		}
			
			
			
			
			
		
        public static void main(String[] args) throws Exception {
                if (args.length != 2) {
                        System.out.println("Needed parameters: ");
                        System.out.println("\t rootFolder (it will contain intermediate crawl data)");
                        System.out.println("\t numberOfCralwers (number of concurrent threads)");
                        return;
                }

                /*
                 * crawlStorageFolder is a folder where intermediate crawl data is
                 * stored.
                 */
                String crawlStorageFolder = args[0];

                /*
                 * numberOfCrawlers shows the number of concurrent threads that should
                 * be initiated for crawling.
                 */
                int numberOfCrawlers = Integer.parseInt(args[1]);
                
                CrawlConfig config = new CrawlConfig();

                config.setCrawlStorageFolder(crawlStorageFolder);

                /*
                 * Be polite: Make sure that we don't send more than 1 request per
                 * second (1000 milliseconds between requests).
                 */
                config.setPolitenessDelay(1000);

                /*
                 * You can set the maximum crawl depth here. The default value is -1 for
                 * unlimited depth
                 */
                config.setMaxDepthOfCrawling(2);

                /*
                 * You can set the maximum number of pages to crawl. The default value
                 * is -1 for unlimited number of pages
                 */
                config.setMaxPagesToFetch(1);

                /*
                 * Do you need to set a proxy? If so, you can use:
                 * config.setProxyHost("proxyserver.example.com");
                 * config.setProxyPort(8080);
                 * 
                 * If your proxy also needs authentication:
                 * config.setProxyUsername(username); config.getProxyPassword(password);
Comment by 

 tharindu...@gmail.com, Sep 15, 2013:
Isn't it proxy.setProxyPassword(password) ?
                 */

                /*
                 * This config parameter can be used to set your crawl to be resumable
                 * (meaning that you can resume the crawl from a previously
                 * interrupted/crashed crawl). Note: if you enable resuming feature and
                 * want to start a fresh crawl, you need to delete the contents of
                 * rootFolder manually.
                 */
                config.setResumableCrawling(false);

                /*
                 * Instantiate the controller for this crawl.
                 */
                PageFetcher pageFetcher = new PageFetcher(config);
                RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
                RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
                CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
                
                
                
                readStopWords();
                
                /*
                 * For each crawl, you need to add some seed urls. These are the first
                 * URLs that are fetched and then the crawler starts following links
                 * which are found in these pages
                 */

//                controller.addSeed("http://www.ics.uci.edu/");
//                controller.addSeed("http://www.ics.uci.edu/~lopes/");
////              controller.addSeed("http://www.ics.uci.edu/~mpufal/");
//                controller.addSeed("http://www.ics.uci.edu/~welling/");
                  controller.addSeed("http://www.ranks.nl/resources/stopwords.html");
                
//TODO:
//1)HASHMAP PF STOP words
//2)...
                /*
                 * Start the crawl. This is a blocking operation, meaning that your code
                 * will reach the line after this only when crawling is finished.
                 */
                controller.start(BasicCrawler.class, numberOfCrawlers);
        }
}