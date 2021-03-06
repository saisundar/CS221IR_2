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

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.Header;

/**
 * @author Yasser Ganjisaffar <lastname at gmail dot com>
 */
public class BasicCrawler extends WebCrawler {

        private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g"
        				+ "|png|tiff?|mid|mp2|mp3|mp4|mkv"
                        + "|wav|avi|mov|mpeg|ram|m4v|pdf"
        				+ "|rm|smil|wmv|swf|wma|zip|rar|gz|bz2|s7z|tgz|tbz2"
                        + "|asm|c|h|cc|cpp|java|cnf"
                        + "|ppt|pptx|doc|docx|data|names|arff|csv|xls|xlsx|rtf|xfa|exe))$");
        
        public static BufferedWriter textOutput;
        public static BufferedWriter htmlOutput;

        /**
         * You should implement this function to specify whether the given url
         * should be crawled or not (based on your crawling logic).
         */
        @Override
        
//        ////// TODO:
        
//      archive contains too many selection parameters
//      flamingo source code
//      http://fano.ics.uci.edu/ca/						  genetic algorithm glider files
//      http://ironwood.ics.uci.edu/start?do=revisions    duplicate page content
//    	http://drzaius.ics.uci.edu/cgi-bin/cvsweb.cgi/    repository
        
        
        public boolean shouldVisit(WebURL url) {
                String href = url.getURL().toLowerCase();
                return !FILTERS.matcher(href).matches() && href.contains(".ics.uci.edu/") 
                		&& !href.contains("calendar.ics.uci.edu") 
                		&& !href.contains("archive.ics.uci.edu")
                		&& !href.startsWith("http://flamingo.ics.uci.edu/releases/")
                		&& !href.startsWith("http://fano.ics.uci.edu/ca")
                		&& !href.startsWith("http://ironwood.ics.uci.edu/start?do=revisions")
                		&& !href.startsWith("http://drzaius.ics.uci.edu/cgi-bin/cvsweb.cgi/");
        }

        /**
         * This function is called when a page is fetched and ready to be processed
         * by your program.
         */
        @Override
        public void visit(Page page) {
                int docid = page.getWebURL().getDocid();
                String url = page.getWebURL().getURL();
                String domain = page.getWebURL().getDomain();
                String path = page.getWebURL().getPath();
                String subDomain = page.getWebURL().getSubDomain();
                String parentUrl = page.getWebURL().getParentUrl();
                String anchor = page.getWebURL().getAnchor();

                System.out.println("Docid: " + docid);
                System.out.println("URL: " + url);
                System.out.println("Domain: '" + domain + "'");
                System.out.println("Sub-domain: '" + subDomain + "'");
                System.out.println("Path: '" + path + "'");
                System.out.println("Parent page: " + parentUrl);
                System.out.println("Anchor text: " + anchor);
                
                if (page.getParseData() instanceof HtmlParseData) {
                        HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
                        String text = htmlParseData.getText();
                        writeToFile(textOutput, text, docid, url, domain, subDomain);
                        String html = htmlParseData.getHtml();
                        writeToFile(htmlOutput, html, docid, url, domain, subDomain);
                        List<WebURL> links = htmlParseData.getOutgoingUrls();

                        System.out.println("Text length: " + text.length());
                        System.out.println("Html length: " + html.length());
                        System.out.println("Number of outgoing links: " + links.size());
                }

                Header[] responseHeaders = page.getFetchResponseHeaders();
                if (responseHeaders != null) {
                        System.out.println("Response headers:");
                        for (Header header : responseHeaders) {
                                System.out.println("\t" + header.getName() + ": " + header.getValue());
                        }
                }
                
                System.out.println("=============");
        }
        
        /* Function to write retrieved page content to file*/
        private synchronized void writeToFile(BufferedWriter out, String text, int docid, String url, String domain,
        		String subDomain)
        {
        	try
        	{
    			out.write("\n!@#$%^&*()_+\n");
    			out.write(docid + "\n");
    			out.write(url + "\n");
    			out.write(domain + "\n");
    			out.write(subDomain + "\n");
    			out.write(text);
    			out.flush();
        	}
        	catch(IOException e)
        	{
        		System.out.println("File write error");
        	}
        }
        
}