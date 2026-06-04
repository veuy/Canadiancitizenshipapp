package com.example.canadiancitizenshipapp.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.canadiancitizenshipapp.data.local.AppDatabase;
import com.example.canadiancitizenshipapp.data.local.Question;
import com.example.canadiancitizenshipapp.data.local.QuestionDao;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository to manage offline question data.
 * Contains literal data from the Canadian Citizenship Study Guide.
 */
public class QuestionRepository {
    private final QuestionDao questionDao;

    public QuestionRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        questionDao = db.questionDao();
        initializeAllQuestions();
    }

    public LiveData<List<Question>> getQuestionsByChapter(String chapterTitle) {
        return questionDao.getQuestionsByChapter(chapterTitle);
    }

    public LiveData<List<Question>> getAllQuestions() {
        return questionDao.getAllQuestions();
    }

    public void getPracticeExamQuestions(int examIndex, RepositoryCallback<List<Question>> callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<Question> all = questionDao.getAllQuestionsList();
            int start = examIndex * 20;
            int end = start + 20;
            if (end <= all.size()) {
                List<Question> examSet = new ArrayList<>(all.subList(start, end));
                java.util.Collections.shuffle(examSet);
                callback.onComplete(examSet);
            } else {
                callback.onComplete(null);
            }
        });
    }

    public void getRandomMockQuestions(RepositoryCallback<List<Question>> callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<Question> all = questionDao.getAllQuestionsList();
            if (all.size() >= 20) {
                java.util.Collections.shuffle(all);
                callback.onComplete(new ArrayList<>(all.subList(0, 20)));
            } else {
                callback.onComplete(null);
            }
        });
    }

    public interface RepositoryCallback<T> {
        void onComplete(T result);
    }

    private void initializeAllQuestions() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            // Check if already populated to avoid duplicates
            // We use a lower threshold to ensure it doesn't wipe every time if data exists
            if (questionDao.getCount() >= 180) return;

            questionDao.deleteAll(); 
            List<Question> q = new ArrayList<>();

            // CHAPTER 1
            String c1 = "Chapter 1: Becoming a Canadian Citizen";
            q.add(new Question("What does becoming a Canadian citizen reflect?", "Commitment, responsibility, and belonging", "Wealth and status", "A legal requirement only", "A business opportunity", "Commitment, responsibility, and belonging", "Citizenship is a milestone of belonging and duty.", c1, false));
            q.add(new Question("For how many years have immigrants helped shape Canada into a diverse society?", "Over 100 years", "Over 200 years", "Over 400 years", "Over 1000 years", "Over 400 years", "Immigrants have shaped Canada for over four centuries.", c1, false));
            q.add(new Question("Which of these is a key principle Canada is built on?", "Absolute monarchy", "Constitutional monarchy", "Unitary state", "Dictatorship", "Constitutional monarchy", "Canada's three principles are Constitutional monarchy, Parliamentary democracy, and Federal system.", c1, false));
            q.add(new Question("What system of government does Canada use?", "Totalitarian", "Federal system of government", "Communist", "Anarchist", "Federal system of government", "The federal system is one of Canada's founding principles.", c1, false));
            q.add(new Question("Canadians are united by which shared value?", "Individual greed", "The rule of law", "Religious uniformity", "Ethnic purity", "The rule of law", "The rule of law, freedom, and respect for institutions unite Canadians.", c1, false));
            q.add(new Question("What is one of the requirements to apply for citizenship?", "Being born in Canada", "Meeting residency and language requirements", "Owning property", "Having a high-paying job", "Meeting residency and language requirements", "Residency and language are core requirements.", c1, false));
            q.add(new Question("What does the citizenship test evaluate?", "Athletic ability", "Knowledge of Canada (history, government, rights)", "Professional skills", "Musical talent", "Knowledge of Canada (history, government, rights)", "The test covers history, government, and rights.", c1, false));
            q.add(new Question("In which languages must you demonstrate ability for the citizenship test?", "English or French", "English or Spanish", "English or Mandarin", "French or German", "English or French", "English and French are the official languages.", c1, false));
            q.add(new Question("What happens at a citizenship ceremony?", "You receive a driver's license", "You take the Oath of Citizenship", "You pay a fine", "You receive a job offer", "You take the Oath of Citizenship", "The ceremony involves the Oath and receiving the certificate.", c1, false));
            q.add(new Question("What does the Oath of Citizenship represent?", "Loyalty to a political party", "Loyalty to Canada through the Sovereign", "Loyalty to your home country", "Loyalty to a specific religion", "Loyalty to Canada through the Sovereign", "The Oath is a commitment to Canada and the Sovereign.", c1, false));
            q.add(new Question("What is a commitment made in the Oath of Citizenship?", "To follow Canadian laws", "To never leave Canada", "To change your religion", "To join the military", "To follow Canadian laws", "Following laws and respecting rights are parts of the Oath.", c1, false));
            q.add(new Question("Whose rights does the Oath specifically mention respecting?", "Only politicians", "Indigenous rights", "Only immigrants", "Business owners", "Indigenous rights", "The updated Oath includes a commitment to respect Indigenous rights.", c1, false));
            q.add(new Question("Citizenship is described as what in Canadian society?", "Passive residency", "Active participation", "A burden", "Optional registration", "Active participation", "Citizenship is more than legal status; it is active involvement.", c1, false));
            q.add(new Question("What is received after taking the Oath of Citizenship?", "A passport", "A citizenship certificate", "A gold medal", "A tax refund", "A citizenship certificate", "The certificate is the official proof of citizenship.", c1, false));
            q.add(new Question("What does the citizenship process involve verifying?", "Your credit score", "Your legal status", "Your height", "Your favorite sport", "Your legal status", "Verifying legal status is the first step.", c1, false));
            q.add(new Question("Which of the following is NOT a founding principle of Canada?", "Parliamentary democracy", "Federal system", "Direct democracy", "Constitutional monarchy", "Direct democracy", "The three are Constitutional Monarchy, Parliamentary Democracy, and Federal System.", c1, false));
            q.add(new Question("Becoming a Canadian citizen is an important milestone.", "True", "False", "", "", "True", "It reflects commitment and belonging.", c1, true));
            q.add(new Question("Immigrants have shaped Canada for only the last 50 years.", "True", "False", "", "", "False", "Immigrants have shaped Canada for over 400 years.", c1, true));
            q.add(new Question("The rule of law is a shared value that unites Canadians.", "True", "False", "", "", "True", "Shared values include the rule of law and freedom.", c1, true));
            q.add(new Question("Passing a citizenship test is always required for everyone.", "True", "False", "", "", "False", "It is required 'if required' (usually based on age).", c1, true));

            // CHAPTER 2
            String c2 = "Chapter 2: Rights and Responsibilities of Citizenship";
            q.add(new Question("Which document from 1215 is a root of Canadian legal tradition?", "The Magna Carta", "The Bill of Rights", "The Declaration of Independence", "The Treaty of Paris", "The Magna Carta", "Centuries of legal tradition include the Magna Carta (1215).", c2, false));
            q.add(new Question("When was the Canadian Charter of Rights and Freedoms created?", "1867", "1982", "1914", "1945", "1982", "The Charter was created in 1982.", c2, false));
            q.add(new Question("Which of the following is a fundamental freedom in Canada?", "Freedom of religion", "Freedom from all taxes", "The right to drive a car", "The right to own a business", "Freedom of religion", "Fundamental freedoms include religion, speech, assembly, and association.", c2, false));
            q.add(new Question("What do mobility rights allow Canadians to do?", "Drive a car without a license", "Live and work anywhere in Canada", "Visit any country without a passport", "Change their name at any time", "Live and work anywhere in Canada", "Mobility rights guarantee the freedom to live and work anywhere in the country.", c2, false));
            q.add(new Question("What are the two official languages of Canada?", "English and Spanish", "English and French", "French and German", "English and Indigenous", "English and French", "English and French are Canada's two official languages.", c2, false));
            q.add(new Question("Who is equal under the law in Canada?", "Only citizens", "Men and women", "Only government officials", "Only property owners", "Men and women", "Men and women are equal under the law in Canada.", c2, false));
            q.add(new Question("Which of these is a responsibility of Canadian citizens?", "To drive a car", "To obey the law", "To own property", "To work in the government", "To obey the law", "Rights come with responsibilities like obeying the law and voting.", c2, false));
            q.add(new Question("What is a legal duty for citizens if called upon?", "Serving on a jury", "Joining the military", "Teaching in schools", "Cleaning the streets", "Serving on a jury", "Serving on a jury when required is a responsibility of citizenship.", c2, false));
            q.add(new Question("Are harmful practices like forced marriage legal in Canada?", "Yes, if traditional", "No, they are strictly illegal and punished", "Only in certain provinces", "Yes, with government permission", "No, they are strictly illegal and punished", "Gender-based violence and forced marriage are strictly illegal.", c2, false));
            q.add(new Question("How can Canadians contribute to defending Canada?", "Armed Forces", "Emergency services", "Community protection roles", "All of the above", "All of the above", "Canadians contribute through military, emergency, and community roles.", c2, false));
            q.add(new Question("Which of the following is a fundamental freedom?", "Freedom of association", "Freedom to not pay rent", "Freedom to speed on roads", "Freedom to avoid jury duty", "Freedom of association", "Association is one of the four fundamental freedoms.", c2, false));
            q.add(new Question("What does 'Aboriginal Rights' protect?", "First Nations, Inuit, and Métis rights", "Only British rights", "Only French rights", "Only newcomer rights", "First Nations, Inuit, and Métis rights", "Aboriginal rights protect the rights of Indigenous peoples.", c2, false));
            q.add(new Question("What is a core national value regarding diverse cultures?", "Assimilation", "Multiculturalism", "Uniformity", "Isolation", "Multiculturalism", "Multiculturalism is respect for diverse cultures.", c2, false));
            q.add(new Question("Voting in elections is considered a...", "Choice only", "Responsibility of citizenship", "Mandatory requirement by law", "Punishment", "Responsibility of citizenship", "Voting is both a right and a responsibility.", c2, false));
            q.add(new Question("Helping your community is a responsibility.", "True", "False", "", "", "True", "Civic participation is a key responsibility.", c2, true));
            q.add(new Question("Military service in Canada is mandatory for all citizens.", "True", "False", "", "", "False", "While military service is voluntary, Canadians contribute in various ways.", c2, true));
            q.add(new Question("Every Canadian has the right to freedom of speech.", "True", "False", "", "", "True", "Freedom of speech and expression is a fundamental freedom.", c2, true));
            q.add(new Question("The Magna Carta was signed in 1982.", "True", "False", "", "", "False", "The Magna Carta was signed in 1215; the Charter was in 1982.", c2, true));
            q.add(new Question("Protecting the environment is a responsibility of citizenship.", "True", "False", "", "", "True", "Citizens are responsible for protecting environment and heritage.", c2, true));
            q.add(new Question("Men and women are not equal under the law in Canada.", "True", "False", "", "", "False", "Equality of men and women is a core value.", c2, true));

            // CHAPTER 3
            String c3 = "Chapter 3: Who We Are – Canadian Identity";
            q.add(new Question("Who are the three founding peoples of Canada?", "Indigenous, French, and British", "French, British, and American", "Indigenous, Spanish, and French", "British, Irish, and Scottish", "Indigenous, French, and British", "Canada's identity is shaped by these three founding groups.", c3, false));
            q.add(new Question("What three groups make up the Indigenous peoples?", "First Nations, Inuit, and Métis", "Cree, Sioux, and Blackfoot", "Algonquin, Mohawk, and Huron", "First Nations, Inuit, and Acadian", "First Nations, Inuit, and Métis", "Indigenous peoples include First Nations, Inuit, and Métis.", c3, false));
            q.add(new Question("Where do most Inuit live?", "Southern Ontario", "Arctic regions", "The Atlantic coast", "The Prairies", "Arctic regions", "Inuit live in the Arctic regions of Canada.", c3, false));
            q.add(new Question("Who are the Métis?", "Indigenous people from Quebec", "People of mixed Indigenous and European ancestry", "The first French settlers", "The first British explorers", "People of mixed Indigenous and European ancestry", "Métis emerged from mixed Indigenous and European ancestry.", c3, false));
            q.add(new Question("Indigenous cultures are deeply connected to what?", "Nature, spiritual beliefs, and community", "Technology and industry", "European politics", "Global trade networks", "Nature, spiritual beliefs, and community", "Their cultures are connected to the land and tradition.", c3, false));
            q.add(new Question("What was a major hardship faced by Indigenous communities historically?", "The fur trade", "Residential schools", "Confederation", "The War of 1812", "Residential schools", "Hardships include residential schools and loss of culture.", c3, false));
            q.add(new Question("In which province do most French speakers live?", "Ontario", "Quebec", "Alberta", "British Columbia", "Quebec", "Most French speakers live in Quebec.", c3, false));
            q.add(new Question("Canada is often called what due to its history of immigration?", "A nation of immigrants", "The new world", "The land of the free", "The British colony", "A nation of immigrants", "Millions have come over the past 200 years.", c3, false));
            q.add(new Question("What is a core national value in Canada regarding culture?", "Uniformity", "Multiculturalism", "Isolationism", "Assimilation", "Multiculturalism", "Multiculturalism is a core national value.", c3, false));
            q.add(new Question("Modern Canadian identity includes the inclusion of which communities?", "Only English-speaking", "Only Christian", "LGBTQ+", "Only property owners", "LGBTQ+", "Modern identity includes diversity and inclusion of LGBTQ+ communities.", c3, false));
            q.add(new Question("Who preserved their cultural identity despite historical challenges?", "Acadians and Quebecers", "Only Loyalists", "Only American settlers", "Only Vikings", "Acadians and Quebecers", "These groups preserved strong identities.", c3, false));
            q.add(new Question("Which of these is NOT a common cultural group in Canada?", "European", "Asian", "Indigenous", "Martian", "Martian", "Main groups include European, Asian, and Indigenous.", c3, false));
            q.add(new Question("What does Canada support regarding religion?", "Uniform religion", "Religious freedom", "No religion", "State religion only", "Religious freedom", "Canada supports religious freedom for all.", c3, false));
            q.add(new Question("What is a core modern Canadian value?", "Equality under the law", "Absolute power", "Religious intolerance", "Ignoring history", "Equality under the law", "Modern identity includes equality and respect for diversity.", c3, false));
            q.add(new Question("Indigenous peoples lived in Canada before Europeans arrived.", "True", "False", "", "", "True", "Indigenous peoples lived in Canada long before European arrival.", c3, true));
            q.add(new Question("English and French are the only languages spoken in Canada.", "True", "False", "", "", "False", "While they are the official languages, many other languages are spoken.", c3, true));
            q.add(new Question("Multiculturalism means that everyone must follow one culture.", "True", "False", "", "", "False", "Multiculturalism is about respect for diverse cultures.", c3, true));
            q.add(new Question("Canada supports religious freedom for all.", "True", "False", "", "", "True", "Religious freedom is supported for all communities.", c3, true));
            q.add(new Question("The Métis are people of mixed Indigenous and European ancestry.", "True", "False", "", "", "True", "This is the definition of Métis identity.", c3, true));
            q.add(new Question("Inuit live primarily in the Prairies.", "True", "False", "", "", "False", "Inuit live primarily in the Arctic regions.", c3, true));

            // CHAPTER 4
            String c4 = "Chapter 4: Canada’s History (Detailed & Structured Timeline)";
            q.add(new Question("Who were the first Europeans to reach Canada?", "The British", "The Vikings", "The French", "The Spanish", "The Vikings", "Around 1000 AD, Vikings settled briefly in Newfoundland.", c4, false));
            q.add(new Question("Who explored the Atlantic coast for England in 1497?", "Jacques Cartier", "John Cabot", "Samuel de Champlain", "Christopher Columbus", "John Cabot", "John Cabot claimed land for the British Crown in 1497.", c4, false));
            q.add(new Question("What does the word 'kanata' mean?", "Country", "Village", "River", "Forest", "Village", "Cartier heard 'kanata', meaning 'village', which became 'Canada'.", c4, false));
            q.add(new Question("When was Québec City founded?", "1604", "1608", "1759", "1867", "1608", "Québec City was founded by the French in 1608.", c4, false));
            q.add(new Question("What was the main economy of New France?", "Gold mining", "Fur trade", "Fishing", "Farming", "Fur trade", "The economy was centered on the fur trade (especially beaver pelts).", c4, false));
            q.add(new Question("In which battle did the British defeat the French in 1759?", "Battle of Vimy Ridge", "Battle of the Plains of Abraham", "Battle of 1812", "Battle of the Atlantic", "Battle of the Plains of Abraham", "This was a major turning point where Britain took control.", c4, false));
            q.add(new Question("What did the Quebec Act of 1774 allow?", "French language and Catholic religion", "British soldiers to leave", "Indigenous independence", "The sale of Alaska", "French language and Catholic religion", "The Act helped avoid rebellion by preserving French rights.", c4, false));
            q.add(new Question("Who were the Loyalists?", "Pro-American settlers", "Pro-British settlers fleeing the American Revolution", "French fur traders", "Indigenous warriors", "Pro-British settlers fleeing the American Revolution", "Loyalists fled to Canada after the American Revolution.", c4, false));
            q.add(new Question("Who was an important Indigenous ally in the War of 1812?", "Louis Riel", "Tecumseh", "John A. Macdonald", "Jacques Cartier", "Tecumseh", "Tecumseh was a key Indigenous ally defending Canada.", c4, false));
            q.add(new Question("When did Canada officially become a country?", "July 1, 1867", "July 4, 1776", "June 24, 1608", "January 1, 1905", "July 1, 1867", "Confederation happened on July 1, 1867.", c4, false));
            q.add(new Question("Who was the first Prime Minister of Canada?", "Sir John A. Macdonald", "Alexander Mackenzie", "Wilfrid Laurier", "Robert Borden", "Sir John A. Macdonald", "Sir John A. Macdonald was the key leader of Confederation.", c4, false));
            q.add(new Question("Who led the Métis resistance and the creation of Manitoba?", "Tecumseh", "Louis Riel", "Samuel de Champlain", "John Cabot", "Louis Riel", "Louis Riel led the resistance against Canadian expansion.", c4, false));
            q.add(new Question("In what year did British Columbia join Confederation?", "1867", "1871", "1898", "1905", "1871", "BC joined in 1871 after Manitoba (1870).", c4, false));
            q.add(new Question("When did Nunavut become a territory?", "1867", "1949", "1982", "1999", "1999", "Nunavut was created in 1999.", c4, false));
            q.add(new Question("Who explored the St. Lawrence River in 1534-1542?", "John Cabot", "Jacques Cartier", "Samuel de Champlain", "Louis Riel", "Jacques Cartier", "Cartier explored the St. Lawrence for France.", c4, false));
            q.add(new Question("What was established in 1849?", "Responsible Government", "Confederation", "New France", "The USA", "Responsible Government", "1849 marked the foundation of modern democracy.", c4, false));
            q.add(new Question("What happened in 1763?", "Britain took control of Canada", "The Vikings arrived", "Confederation", "Manitoba joined", "Britain took control of Canada", "1763 was the end of New France.", c4, false));
            q.add(new Question("Which provinces were the original four in 1867?", "ON, QC, NS, NB", "BC, AB, SK, MB", "ON, QC, PEI, NL", "NS, NB, PEI, QC", "ON, QC, NS, NB", "Ontario, Quebec, Nova Scotia, and New Brunswick were the first.", c4, false));
            q.add(new Question("The War of 1812 was fought between Canada and Britain.", "True", "False", "", "", "False", "It was fought between Britain (Canada) and the United States.", c4, true));
            q.add(new Question("Confederation united the colonies into one nation.", "True", "False", "", "", "True", "Confederation in 1867 created the federal system.", c4, true));
            q.add(new Question("The first provinces were Ontario, Quebec, Nova Scotia, and New Brunswick.", "True", "False", "", "", "True", "These were the four original provinces in 1867.", c4, true));
            q.add(new Question("Canada gained independence through a violent revolution.", "True", "False", "", "", "False", "Canada achieved gradual independence through compromise.", c4, true));
            q.add(new Question("Jacques Cartier heard the word 'kanata', which became 'Canada'.", "True", "False", "", "", "True", "Kanata means village.", c4, true));
            q.add(new Question("Newfoundland joined Confederation in 1867.", "True", "False", "", "", "False", "Newfoundland joined in 1949.", c4, true));
            q.add(new Question("Louis Riel led uprisings in Manitoba.", "True", "False", "", "", "True", "He led the Métis resistance.", c4, true));

            // CHAPTER 5
            String c5 = "Chapter 5: Modern Canada (Expanded)";
            q.add(new Question("What did Canada gain after fighting in WWI?", "More territory in Europe", "International recognition", "A new flag", "Total independence from the King", "International recognition", "Canada emerged as a respected global power after the wars.", c5, false));
            q.add(new Question("Which act in 1931 made Canada self-governing?", "The British North America Act", "The Statute of Westminster", "The Quebec Act", "The Constitution Act", "The Statute of Westminster", "The 1931 Statute made Canada self-governing.", c5, false));
            q.add(new Question("What happened in 1982 regarding Canada's independence?", "Confederation", "The Constitution Act was signed", "The War of 1812 ended", "The first election was held", "The Constitution Act was signed", "In 1982, Canada gained full legal independence.", c5, false));
            q.add(new Question("What does the Charter of Rights and Freedoms guarantee?", "Fundamental freedoms and equality rights", "Free cars for all", "No taxes for citizens", "A job for everyone", "Fundamental freedoms and equality rights", "The Charter protects all Canadians from unfair treatment.", c5, false));
            q.add(new Question("Since when did immigration increase significantly in modern Canada?", "1867", "The 1970s", "The 1940s", "The 1990s", "The 1970s", "Immigration increased and multiculturalism was adopted since the 70s.", c5, false));
            q.add(new Question("What does modern Canada recognize regarding Indigenous history?", "The fur trade was great", "Residential school harm", "Confederation was perfect", "The War of 1812 was easy", "Residential school harm", "Canada recognizes the harm and the need for reconciliation.", c5, false));
            q.add(new Question("Which of these is a key industry in Canada's strong economy?", "Oil & gas", "Mining", "Manufacturing", "All of the above", "All of the above", "Canada is resource-rich and industrialized in many sectors.", c5, false));
            q.add(new Question("What marked Canada as a respected global power?", "Contributions in World Wars", "The fur trade", "The Vikings", "Buying Alaska", "Contributions in World Wars", "Contributions in WWI and WWII strengthened Canada's role.", c5, false));
            q.add(new Question("What was adopted along with increased immigration in the 1970s?", "Multiculturalism", "Assimilation", "Monoculturalism", "Isolationism", "Multiculturalism", "Canada adopted multiculturalism in the 1970s.", c5, false));
            q.add(new Question("Which values define modern Canada today?", "Equality and Freedom", "Dictatorship", "Religious uniformity", "Ignoring rights", "Equality and Freedom", "Equality, freedom, and rule of law are core modern values.", c5, false));
            q.add(new Question("In 1982, the Charter of Rights and Freedoms was created.", "True", "False", "", "", "True", "It was part of the 1982 Constitution Act.", c5, true));
            q.add(new Question("Canada fighting in the World Wars strengthened its independence.", "True", "False", "", "", "True", "Contributions in Europe helped Canada emerge as a global power.", c5, true));
            q.add(new Question("Multiculturalism is considered a core strength of modern Canada.", "True", "False", "", "", "True", "Diversity is a core strength of the nation.", c5, true));
            q.add(new Question("The Statute of Westminster was signed in 1867.", "True", "False", "", "", "False", "It was signed in 1931.", c5, true));
            q.add(new Question("Canada recognized the harm of residential schools.", "True", "False", "", "", "True", "Reconciliation efforts acknowledge this history.", c5, true));

            // CHAPTER 6
            String c6 = "Chapter 6: How Canadians Govern Themselves (Expanded)";
            q.add(new Question("What type of monarchy is Canada?", "Absolute", "Constitutional", "Elective", "Temporary", "Constitutional", "Canada is a constitutional monarchy.", c6, false));
            q.add(new Question("Who is the head of state in Canada?", "The Prime Minister", "The King/Queen", "The Governor General", "The Speaker of the House", "The King/Queen", "The Sovereign is the head of state.", c6, false));
            q.add(new Question("Which level of government handles currency and defence?", "Federal", "Provincial", "Municipal", "Territorial", "Federal", "The federal government handles national issues like defence.", c6, false));
            q.add(new Question("Which level of government handles health care and education?", "Federal", "Provincial/Territorial", "Municipal", "School boards", "Provincial/Territorial", "Provinces handle health and education.", c6, false));
            q.add(new Question("What does the municipal government handle?", "Trade and currency", "Local services like roads and fire", "Immigration", "Natural resources", "Local services like roads and fire", "Municipalities handle local infrastructure and services.", c6, false));
            q.add(new Question("Which part of Parliament is the main law-making body?", "The Senate", "The House of Commons", "The Governor General", "The Cabinet", "The House of Commons", "Elected MPs in the Commons are the main law-makers.", c6, false));
            q.add(new Question("Who are the members of the Senate?", "Elected by citizens", "Appointed members", "Selected by the military", "Chosen by the Prime Minister's family", "Appointed members", "Senators are appointed, not elected.", c6, false));
            q.add(new Question("Who is the leader of the government?", "The King", "The Prime Minister", "The Governor General", "The Chief Justice", "The Prime Minister", "The PM is the leader of the government.", c6, false));
            q.add(new Question("What does the 'Rule of Law' mean?", "The King makes all laws", "Everyone must follow the law", "Only citizens follow laws", "The government is above the law", "Everyone must follow the law", "No one is above the law, including the government.", c6, false));
            q.add(new Question("Which level handles natural resources?", "Provincial", "Federal", "Municipal", "United Nations", "Provincial", "Natural resources are managed by provinces.", c6, false));
            q.add(new Question("How many parts are in Canada's Parliament?", "One", "Two", "Three", "Four", "Three", "The Sovereign, the Senate, and the House of Commons.", c6, false));
            q.add(new Question("The House of Commons is made up of elected MPs.", "True", "False", "", "", "True", "MPs are elected by citizens in their ridings.", c6, true));
            q.add(new Question("The federal government handles local fire services.", "True", "False", "", "", "False", "Local services are handled by the municipal government.", c6, true));
            q.add(new Question("Canada's system is a parliamentary democracy.", "True", "False", "", "", "True", "Elected officials run the government in this system.", c6, true));
            q.add(new Question("The Prime Minister is the head of state.", "True", "False", "", "", "False", "The Sovereign is head of state; the PM is head of government.", c6, true));
            q.add(new Question("The Senate reviews laws passed by the House of Commons.", "True", "False", "", "", "True", "The Senate is the house of 'sober second thought'.", c6, true));

            // CHAPTER 7
            String c7 = "Chapter 7: Federal Elections (Expanded)";
            q.add(new Question("What is a requirement to vote in a federal election?", "Be 21 years old", "Be a Canadian citizen", "Own property", "Be born in Canada", "Be a Canadian citizen", "You must be a citizen and 18+ to vote.", c7, false));
            q.add(new Question("At what age are you eligible to vote in Canada?", "16", "18", "19", "21", "18", "The voting age is 18.", c7, false));
            q.add(new Question("What is Canada's electoral system called?", "Proportional representation", "First-Past-the-Post", "Ranked choice", "Direct election", "First-Past-the-Post", "The candidate with the most votes wins.", c7, false));
            q.add(new Question("What is a riding (electoral district)?", "A government building", "A geographic area that elects one MP", "A political party's office", "A place where the King lives", "A geographic area that elects one MP", "Canada is divided into ridings for representation.", c7, false));
            q.add(new Question("Who represents your local community in Parliament?", "The Prime Minister", "Your MP (Member of Parliament)", "The Governor General", "A Senator", "Your MP (Member of Parliament)", "Your MP represents your local riding.", c7, false));
            q.add(new Question("Which party forms the government after an election?", "The party with the most seats", "The party with the oldest leader", "The party that the King likes", "The party that spends the most money", "The party with the most seats", "The leader of that party becomes Prime Minister.", c7, false));
            q.add(new Question("What is a majority government?", "Over 25% of seats", "Over 50% of seats", "Less than 50% of seats", "All the seats", "Over 50% of seats", "A majority government has more than half the seats in the Commons.", c7, false));
            q.add(new Question("What is a minority government?", "Over 50% of seats", "Less than 50% of seats", "A government with no leader", "A government with no parties", "Less than 50% of seats", "A minority government needs support from other parties.", c7, false));
            q.add(new Question("Why do elections matter?", "They hold leaders accountable", "They allow peaceful change of power", "They reflect public opinion", "All of the above", "All of the above", "Elections are the foundation of democracy.", c7, false));
            q.add(new Question("Which of these is NOT a major federal political party?", "Liberal Party", "Conservative Party", "The Viking Party", "NDP", "The Viking Party", "Liberal, Conservative, and NDP are real parties.", c7, false));
            q.add(new Question("Who chooses Cabinet ministers?", "The King", "The Prime Minister", "The Governor General", "The Voters", "The Prime Minister", "The PM chooses ministers to lead departments.", c7, false));
            q.add(new Question("Voting in Canada is mandatory by law.", "True", "False", "", "", "False", "Voting is voluntary but strongly encouraged.", c7, true));
            q.add(new Question("A candidate must get over 50% of the votes to win a riding.", "True", "False", "", "", "False", "In 'First-Past-the-Post', you just need the most votes.", c7, true));
            q.add(new Question("The Prime Minister chooses Cabinet ministers.", "True", "False", "", "", "True", "The PM leads the government and chooses the Cabinet.", c7, true));
            q.add(new Question("Voting is a secret ballot in Canada.", "True", "False", "", "", "True", "No one knows who you voted for.", c7, true));
            q.add(new Question("A riding elects two MPs.", "True", "False", "", "", "False", "Each riding elects exactly one MP.", c7, true));

            // CHAPTER 8
            String c8 = "Chapter 8: The Justice System (Expanded)";
            q.add(new Question("What principle means everyone is innocent until proven guilty?", "The Rule of Law", "Presumption of Innocence", "Criminal Law", "Civil Law", "Presumption of Innocence", "The burden of proof is on the prosecution.", c8, false));
            q.add(new Question("Which type of law deals with crimes like theft or assault?", "Civil Law", "Criminal Law", "Family Law", "Property Law", "Criminal Law", "The government prosecutes offenders in criminal law.", c8, false));
            q.add(new Question("What does civil law resolve?", "Crimes against the state", "Disputes between individuals", "International borders", "Military actions", "Disputes between individuals", "Civil law covers things like contracts and property.", c8, false));
            q.add(new Question("Which is the highest court in Canada?", "Provincial Court", "Superior Court", "Supreme Court of Canada", "The Prime Minister's Court", "Supreme Court of Canada", "The Supreme Court makes final legal decisions.", c8, false));
            q.add(new Question("What is a right of an accused person?", "To choose their judge", "A fair trial and a lawyer", "To avoid all questioning", "To leave the country", "A fair trial and a lawyer", "Accused persons have the right to a fair trial.", c8, false));
            q.add(new Question("What is the role of the police?", "To make the laws", "To enforce laws and protect citizens", "To judge people in court", "To run for election", "To enforce laws and protect citizens", "Police investigate crimes and ensure public safety.", c8, false));
            q.add(new Question("What is jury duty?", "A paid job in the government", "A legal duty for ordinary citizens", "A choice for police officers", "A task for the Prime Minister", "A legal duty for ordinary citizens", "Citizens decide the outcome of some cases in a jury.", c8, false));
            q.add(new Question("Which court makes final decisions on legal issues?", "Superior Court", "Supreme Court of Canada", "Tax Court", "Military Court", "Supreme Court of Canada", "The Supreme Court is the final authority.", c8, false));
            q.add(new Question("What must citizens do regarding police authority?", "Ignore it", "Respect it", "Fight it", "Vote for it", "Respect it", "Citizens must respect police and follow laws.", c8, false));
            q.add(new Question("The Rule of Law means the government is above the law.", "True", "False", "", "", "False", "No one is above the law, including the government.", c8, true));
            q.add(new Question("The Supreme Court of Canada is the highest court in the land.", "True", "False", "", "", "True", "It makes final decisions on legal issues.", c8, true));
            q.add(new Question("Criminal law deals with disputes between individuals.", "True", "False", "", "", "False", "Criminal law deals with crimes; civil law deals with disputes.", c8, true));
            q.add(new Question("Juries are made up of ordinary citizens.", "True", "False", "", "", "True", "Jury duty is a civic responsibility for citizens.", c8, true));

            // CHAPTER 9
            String c9 = "Chapter 9: Canadian Symbols (Expanded)";
            q.add(new Question("When was the current Canadian flag adopted?", "1867", "1965", "1921", "1982", "1965", "The red and white flag was adopted in 1965.", c9, false));
            q.add(new Question("What is the title of the national anthem?", "God Save the King", "O Canada", "The Maple Leaf Forever", "True North", "O Canada", "O Canada is the official national anthem.", c9, false));
            q.add(new Question("Who represents the King in Canada?", "The Prime Minister", "The Governor General", "The Speaker of the House", "The Chief Justice", "The Governor General", "The GG represents the Sovereign in Canada.", c9, false));
            q.add(new Question("Which animal is a symbol of hard work and history in the fur trade?", "The Moose", "The Beaver", "The Polar Bear", "The Wolf", "The Beaver", "The beaver is a long-standing symbol of Canada.", c9, false));
            q.add(new Question("What is the national winter sport of Canada?", "Soccer", "Hockey", "Lacrosse", "Skiing", "Hockey", "Hockey is the national winter sport.", c9, false));
            q.add(new Question("What is the national summer sport of Canada?", "Baseball", "Lacrosse", "Canoeing", "Tennis", "Lacrosse", "Lacrosse is the national summer sport.", c9, false));
            q.add(new Question("When were red and white officially adopted as national colours?", "1867", "1921", "1965", "1982", "1921", "The colours were officially adopted in 1921.", c9, false));
            q.add(new Question("What does the Crown symbolize in Canada?", "Stability and tradition", "Absolute power", "Revolution", "Wealth", "Stability and tradition", "The Crown represents the stability of the monarchy.", c9, false));
            q.add(new Question("The Maple Leaf is a long-standing symbol of Canada.", "True", "False", "", "", "True", "It has been a symbol for centuries.", c9, true));
            q.add(new Question("The Governor General is the head of state.", "True", "False", "", "", "False", "The King is head of state; the GG is the representative.", c9, true));
            q.add(new Question("Red and white were adopted as national colours in 1965.", "True", "False", "", "", "False", "They were adopted in 1921; the flag was 1965.", c9, true));

            // CHAPTER 10
            String c10 = "Chapter 10: Canada’s Economy (Expanded)";
            q.add(new Question("What type of economy does Canada have?", "Purely socialist", "Mixed economy", "Purely capitalist", "Command economy", "Mixed economy", "Canada has a mix of free market and government involvement.", c10, false));
            q.add(new Question("Which resource is a major source of wealth for Canada?", "Oil and gas", "Forests", "Minerals", "All of the above", "All of the above", "Canada is rich in many natural resources.", c10, false));
            q.add(new Question("Which sector includes farming, mining, and fishing?", "Primary Sector", "Secondary Sector", "Tertiary Sector", "Quaternary Sector", "Primary Sector", "The primary sector deals with raw materials.", c10, false));
            q.add(new Question("Which sector includes manufacturing and construction?", "Primary Sector", "Secondary Sector", "Tertiary Sector", "Retail Sector", "Secondary Sector", "The secondary sector processes raw materials.", c10, false));
            q.add(new Question("Which sector includes banking, healthcare, and retail?", "Primary Sector", "Secondary Sector", "Tertiary Sector", "Industrial Sector", "Tertiary Sector", "The tertiary sector is the service industry.", c10, false));
            q.add(new Question("Who is Canada’s major trading partner?", "China", "United States", "United Kingdom", "Mexico", "United States", "Canada depends heavily on trade with the US.", c10, false));
            q.add(new Question("What is a major export for Canada?", "Oil", "Cars", "Wood", "All of the above", "All of the above", "Oil, cars, wood, and food are major exports.", c10, false));
            q.add(new Question("Canada has a highly skilled and educated workforce.", "True", "False", "", "", "True", "Education levels and skilled labor are strengths.", c10, true));
            q.add(new Question("Natural resources are not important to the Canadian economy.", "True", "False", "", "", "False", "They are a major source of national wealth.", c10, true));
            q.add(new Question("The tertiary sector is the largest part of the economy.", "True", "False", "", "", "True", "Services (Tertiary) dominate the modern workforce.", c10, true));

            // CHAPTER 11
            String c11 = "Chapter 11: Canada’s Regions (Expanded)";
            q.add(new Question("Which region is known for its fishing industry and coastal culture?", "Central Canada", "Atlantic Provinces", "The Prairies", "Northern Territories", "Atlantic Provinces", "The Atlantic provinces include NS, NB, PEI, and NL.", c11, false));
            q.add(new Question("Which region contains the largest population and major cities like Toronto and Montreal?", "Western Canada", "Central Canada", "The Maritimes", "The North", "Central Canada", "Central Canada consists of Ontario and Quebec.", c11, false));
            q.add(new Question("Which region is famous for wheat farming and Alberta’s oil and gas?", "Atlantic Provinces", "Prairie Provinces", "West Coast", "Northern Territories", "Prairie Provinces", "The Prairies include MB, SK, and AB.", c11, false));
            q.add(new Question("Which region features mountains, forests, and Pacific trade?", "The Prairies", "West Coast (British Columbia)", "Atlantic Provinces", "Central Canada", "West Coast (British Columbia)", "BC is known for its geography and trade.", c11, false));
            q.add(new Question("Which region has an Arctic climate and rich natural resources?", "The Maritimes", "Northern Territories", "Central Canada", "The Prairies", "Northern Territories", "The North includes YT, NT, and NU.", c11, false));
            q.add(new Question("Which province is in the West Coast region?", "Alberta", "British Columbia", "Manitoba", "Ontario", "British Columbia", "BC is the only province on the West Coast.", c11, false));
            q.add(new Question("Ontario and Quebec are part of Central Canada.", "True", "False", "", "", "True", "They are the two provinces in this region.", c11, true));
            q.add(new Question("The Atlantic provinces include Alberta.", "True", "False", "", "", "False", "Alberta is a Prairie province.", c11, true));
            q.add(new Question("The Northern Territories feature an Arctic climate.", "True", "False", "", "", "True", "The North is known for its cold, Arctic climate.", c11, true));

            // CHAPTER 12
            String c12 = "Chapter 12: Study & Review Guide (Expanded)";
            q.add(new Question("What should you focus on when studying history?", "Memorizing every name", "Important dates and concepts", "Only the last 10 years", "American history", "Important dates and concepts", "Understanding concepts is key for the test.", c12, false));
            q.add(new Question("Which of these is a topic you should know for the test?", "Rights and responsibilities", "Government structure", "Symbols and Geography", "All of the above", "All of the above", "All these topics are covered in the citizenship test.", c12, false));
            q.add(new Question("Success on the test comes from what?", "Luck", "Understanding, practice, and consistency", "Only reading the flag chapter", "Guessing the answers", "Understanding, practice, and consistency", "Consistent review and practice lead to success.", c12, false));
            q.add(new Question("What is a practice strategy for the test?", "Review mistakes", "Take mock tests", "Study weak areas", "All of the above", "All of the above", "Preparation involves review, tests, and focused study.", c12, false));
            q.add(new Question("The citizenship test includes true/false questions.", "True", "False", "", "", "True", "The test uses multiple-choice and true/false formats.", c12, true));
            q.add(new Question("Success comes from luck.", "True", "False", "", "", "False", "Success comes from preparation and understanding.", c12, true));

            questionDao.insertAll(q);
        });
    }
}
