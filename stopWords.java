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
			
			
			
			
			