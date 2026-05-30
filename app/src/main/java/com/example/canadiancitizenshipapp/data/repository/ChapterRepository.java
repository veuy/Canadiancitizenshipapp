package com.example.canadiancitizenshipapp.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.canadiancitizenshipapp.data.local.AppDatabase;
import com.example.canadiancitizenshipapp.data.local.Chapter;
import com.example.canadiancitizenshipapp.data.local.ChapterDao;
import java.util.ArrayList;
import java.util.List;

public class ChapterRepository {
    private final ChapterDao chapterDao;

    public ChapterRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        chapterDao = db.chapterDao();
        initializeChapters();
    }

    public LiveData<List<Chapter>> getAllChapters() {
        return chapterDao.getAllChapters();
    }

    public LiveData<List<Chapter>> searchChapters(String query) {
        return chapterDao.searchChapters("%" + query + "%");
    }

    public void updateChapter(Chapter chapter) {
        AppDatabase.databaseWriteExecutor.execute(() -> chapterDao.update(chapter));
    }

    private void initializeChapters() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            chapterDao.deleteAll();
            
            List<Chapter> chapters = new ArrayList<>();
            
            chapters.add(new Chapter("Chapter 1: Becoming a Canadian Citizen", 
                "Becoming a Canadian citizen is an important milestone that reflects commitment, responsibility, and belonging. Canada has a long history of welcoming newcomers, and for over 400 years, immigrants have helped shape the country into a diverse and prosperous society.<br><br>" +
                "<b>Canada is built on three key principles:</b><br>" +
                "• Constitutional monarchy<br>" +
                "• Parliamentary democracy<br>" +
                "• Federal system of government<br><br>" +
                "These systems ensure that Canadians are united by shared values such as the rule of law, freedom, and respect for institutions.<br><br>" +
                "<b>The Citizenship Process</b><br>" +
                "Applying for citizenship involves:<br>" +
                "• Verifying your legal status<br>" +
                "• Meeting residency and language requirements<br>" +
                "• Passing a citizenship test (if required)<br><br>" +
                "<b>The test evaluates:</b><br>" +
                "• Knowledge of Canada (history, government, rights)<br>" +
                "• Language ability in English or French<br><br>" +
                "If successful, you attend a citizenship ceremony where you:<br>" +
                "• Take the Oath of Citizenship<br>" +
                "• Receive your certificate<br><br>" +
                "<b>The Oath of Citizenship</b><br>" +
                "The oath represents loyalty to Canada through the Sovereign and commitment to:<br>" +
                "• Follow Canadian laws<br>" +
                "• Respect Indigenous rights<br>" +
                "• Fulfill civic duties<br><br>" +
                "<i><b>Key Idea:</b> Citizenship is not just legal status — it is active participation in Canadian society.</i>"));

            chapters.add(new Chapter("Chapter 2: Rights and Responsibilities of Citizenship", 
                "Canadian citizenship provides both freedoms and duties. These are rooted in centuries of legal tradition, including the Magna Carta (1215) and the Canadian Charter of Rights and Freedoms (1982).<br><br>" +
                "<b>Fundamental Freedoms:</b><br>" +
                "Every Canadian has the right to:<br>" +
                "• Freedom of religion<br>" +
                "• Freedom of speech and expression<br>" +
                "• Freedom of assembly<br>" +
                "• Freedom of association<br><br>" +
                "<b>Key Rights:</b><br>" +
                "• <b>Mobility Rights:</b> Live and work anywhere in Canada<br>" +
                "• <b>Language Rights:</b> English and French are official languages<br>" +
                "• <b>Aboriginal Rights:</b> Protection of First Nations, Inuit, and Métis rights<br>" +
                "• <b>Multiculturalism:</b> Respect for diverse cultures<br><br>" +
                "<b>Equality in Canada:</b><br>" +
                "Men and women are equal under the law. Harmful practices such as forced marriage or gender-based violence are strictly illegal and punished.<br><br>" +
                "<b>Responsibilities of Citizens:</b><br>" +
                "Rights come with responsibilities:<br>" +
                "• Obey the law<br>" +
                "• Take care of yourself and your family<br>" +
                "• Serve on a jury when required<br>" +
                "• Vote in elections<br>" +
                "• Help your community<br>" +
                "• Protect the environment and heritage<br><br>" +
                "<b>Defending Canada:</b><br>" +
                "While military service is voluntary, Canadians contribute through:<br>" +
                "• Armed Forces<br>" +
                "• Emergency services<br>" +
                "• Community protection roles<br><br>" +
                "<i><b>Key Idea:</b> A strong country depends on citizens who both enjoy their rights and fulfill their responsibilities.</i>"));

            chapters.add(new Chapter("Chapter 3: Who We Are – Canadian Identity", 
                "Canada’s identity is shaped by three founding peoples:<br>" +
                "• Indigenous (First Nations, Inuit, Métis)<br>" +
                "• French<br>" +
                "• British<br><br>" +
                "<b>Indigenous Peoples:</b><br>" +
                "Indigenous peoples lived in Canada long before European arrival. They include:<br>" +
                "• <b>First Nations</b> (many communities across Canada)<br>" +
                "• <b>Inuit</b> (Arctic regions)<br>" +
                "• <b>Métis</b> (mixed Indigenous and European ancestry)<br><br>" +
                "Their cultures are deeply connected to nature, spiritual beliefs, and community traditions. History includes hardships such as residential schools and loss of language and culture. Today, Indigenous communities are rebuilding and contributing strongly to Canadian society.<br><br>" +
                "<b>English and French Heritage:</b><br>" +
                "Canada has two official languages: <b>English</b> and <b>French</b>. Most French speakers live in Quebec, but communities exist across Canada. Groups like the Acadians and Quebecers have preserved strong cultural identities.<br><br>" +
                "<b>Immigration and Diversity:</b><br>" +
                "Canada is often called a nation of immigrants. Millions have come over the past 200 years. Multiculturalism is a core national value. Common cultural groups include European, Asian, and Indigenous peoples.<br><br>" +
                "<b>Religion and Society:</b><br>" +
                "Canada supports religious freedom. While many identify as Christian, there are growing communities of Muslims, Hindus, Sikhs, Jews, and non-religious individuals.<br><br>" +
                "<b>Canadian Values:</b><br>" +
                "Modern Canadian identity includes respect for diversity, equality under the law, peace, order, and good government, and inclusion of LGBTQ+ communities.<br><br>" +
                "<i><b>Key Idea:</b> Canada’s strength comes from its diversity united by shared values.</i>"));

            chapters.add(new Chapter("Chapter 4: Canada’s History (Detailed & Structured Timeline)", 
                "Canada’s history is a story of Indigenous foundations, European colonization, conflict, cooperation, and gradual independence.<br><br>" +
                "<b>1. Indigenous Foundations (Before 1500s)</b><br>" +
                "Long before Europeans arrived, Canada was home to diverse Indigenous civilizations.<br>" +
                "• <b>First Nations:</b> across forests, plains, and coastal regions<br>" +
                "• <b>Inuit:</b> Arctic regions<br>" +
                "• <b>Métis:</b> emerged from Indigenous and European ancestry<br><br>" +
                "<b>2. Early European Exploration (1000–1600s)</b><br>" +
                "• <b>1000 AD – Vikings:</b> First Europeans to reach Canada (Newfoundland).<br>" +
                "• <b>1497 – John Cabot:</b> Explored Atlantic coast for England.<br>" +
                "• <b>1534–1542 – Jacques Cartier:</b> Explored St. Lawrence River; heard 'kanata' (village), which became 'Canada'.<br><br>" +
                "<b>3. New France (1604–1759)</b><br>" +
                "France established settlements (Acadia in 1604, Québec City in 1608). The economy centered on the <b>fur trade</b> (beaver pelts) and strong partnerships with Indigenous peoples.<br><br>" +
                "<b>4. British Conquest (1759–1763)</b><br>" +
                "• <b>1759 – Battle of the Plains of Abraham:</b> British defeat French forces.<br>" +
                "• <b>1763 – Britain:</b> takes control of Canada.<br><br>" +
                "<b>5. The Quebec Act (1774)</b><br>" +
                "Britain allowed French language, Catholic religion, and French civil law. This shaped Canada’s identity as <b>bilingual and multicultural</b>.<br><br>" +
                "<b>6. American Revolution & Loyalists (1776–1783)</b><br>" +
                "Loyalists (pro-British) fled to Canada, increasing the English-speaking population.<br><br>" +
                "<b>7. War of 1812 (1812–1814)</b><br>" +
                "The US attempted to invade. British soldiers, Canadian settlers, and Indigenous allies (e.g., <b>Tecumseh</b>) defended Canada.<br><br>" +
                "<b>8. Growth of Democracy (1791–1849)</b><br>" +
                "• <b>1791:</b> Canada divided into Upper and Lower Canada.<br>" +
                "• <b>1837–1838:</b> Rebellions for political power led to reforms.<br>" +
                "• <b>1849:</b> Responsible Government established.<br><br>" +
                "<b>9. Confederation (July 1, 1867)</b><br>" +
                "Canada officially became a country uniting Ontario, Quebec, Nova Scotia, and New Brunswick. <b>Sir John A. Macdonald</b> was the key leader.<br><br>" +
                "<b>10. Expansion (1867–1999)</b><br>" +
                "Canada expanded: Manitoba (1870), BC (1871), PEI (1873), Alberta & Saskatchewan (1905), Newfoundland (1949), Nunavut (1999).<br><br>" +
                "<b>11. Indigenous & Western Conflicts</b><br>" +
                "<b>Louis Riel & Métis Resistance</b> opposed expansion, leading to the creation of Manitoba."));

            chapters.add(new Chapter("Chapter 5: Modern Canada (Expanded)", 
                "Modern Canada developed in the 20th century into a global, diverse, and independent country.<br><br>" +
                "<b>1. Canada in World Wars</b><br>" +
                "• <b>World War I (1914–1918):</b> Gained international recognition.<br>" +
                "• <b>World War II (1939–1945):</b> Major contributions in Europe; strengthened independence.<br><br>" +
                "<b>2. Independence from Britain</b><br>" +
                "• <b>1931 – Statute of Westminster:</b> Canada became self-governing.<br>" +
                "• <b>1982 – Constitution Act:</b> Full legal independence; Charter of Rights and Freedoms created.<br><br>" +
                "<b>3. Charter of Rights and Freedoms</b><br>" +
                "Guarantees fundamental freedoms, equality rights, and legal rights, protecting all Canadians from unfair treatment.<br><br>" +
                "<b>4. Economic Growth</b><br>" +
                "Canada became industrialized and resource-rich. Key industries include <b>oil & gas, mining, manufacturing, and technology</b>.<br><br>" +
                "<b>5. Immigration & Multiculturalism</b><br>" +
                "Since the 1970s, immigration increased and multiculturalism was adopted, resulting in a diverse population.<br><br>" +
                "<b>6. Indigenous Reconciliation</b><br>" +
                "Modern Canada recognizes residential school harm and the need for reconciliation through apologies, legal protections, and cultural revival.<br><br>" +
                "<b>7. Canadian Values Today:</b> Equality, freedom, respect for diversity, rule of law, and a peaceful society."));

            chapters.add(new Chapter("Chapter 6: How Canadians Govern Themselves (Expanded)", 
                "Canada’s government is structured to balance power and represent citizens.<br><br>" +
                "<b>1. System of Government:</b><br>" +
                "Canada is a <b>constitutional monarchy</b>, a <b>parliamentary democracy</b>, and a <b>federal state</b>.<br><br>" +
                "<b>2. Three Levels of Government:</b><br>" +
                "• <b>Federal:</b> Defence, immigration, trade, currency.<br>" +
                "• <b>Provincial/Territorial:</b> Health care, education, natural resources.<br>" +
                "• <b>Municipal:</b> Local services like roads, police, and fire services.<br><br>" +
                "<b>3. Parliament:</b><br>" +
                "Consists of three parts:<br>" +
                "1. <b>The Sovereign</b> (King/Queen)<br>" +
                "2. <b>House of Commons</b> (elected MPs, main law-making body)<br>" +
                "3. <b>Senate</b> (appointed members who review laws)<br><br>" +
                "<b>4. Prime Minister:</b><br>" +
                "Leader of the government, usually the leader of the majority party; makes major decisions.<br><br>" +
                "<b>5. Rule of Law:</b><br>" +
                "Everyone must follow the law; the government is not above it, ensuring fairness and justice."));

            chapters.add(new Chapter("Chapter 7: Federal Elections (Expanded)", 
                "Elections are the foundation of Canada’s democracy, allowing citizens to choose their representatives.<br><br>" +
                "<b>1. Who Can Vote:</b><br>" +
                "Must be a Canadian citizen, 18+ years old. Voting is <b>secret</b> and <b>voluntary</b> but strongly encouraged.<br><br>" +
                "<b>2. Electoral System:</b><br>" +
                "Canada uses <b>'First-Past-the-Post'</b> – the candidate with the most votes wins, even without a majority (over 50%).<br><br>" +
                "<b>3. Ridings (Electoral Districts):</b><br>" +
                "Canada is divided into ridings; each elects one <b>Member of Parliament (MP)</b> to represent the local community.<br><br>" +
                "<b>4. Political Parties:</b><br>" +
                "Major parties include Liberal, Conservative, NDP, Bloc Québécois, and Green Party.<br><br>" +
                "<b>5. Forming Government:</b><br>" +
                "The party with the most seats forms government. <br>" +
                "• <b>Majority Government:</b> over 50% seats<br>" +
                "• <b>Minority Government:</b> less than 50%, needs support<br><br>" +
                "<b>6. The Role of the Prime Minister:</b><br>" +
                "Leads the government, chooses Cabinet ministers, and sets national policies."));

            chapters.add(new Chapter("Chapter 8: The Justice System (Expanded)", 
                "Canada’s justice system ensures fairness, equality, and protection of rights.<br><br>" +
                "<b>1. Rule of Law:</b> Everyone must obey the law, and laws apply equally to all, including the government.<br><br>" +
                "<b>2. Presumption of Innocence:</b> You are innocent until proven guilty; the burden of proof is on the prosecution.<br><br>" +
                "<b>3. Types of Law:</b><br>" +
                "• <b>Criminal Law:</b> Deals with crimes (theft, assault); government prosecutes.<br>" +
                "• <b>Civil Law:</b> Resolves disputes between individuals (contracts, property).<br><br>" +
                "<b>4. Courts in Canada:</b> Provincial courts, Superior courts, and the <b>Supreme Court of Canada</b> (highest court).<br><br>" +
                "<b>5. Rights of the Accused:</b> Right to a fair trial, a lawyer, and to be tried within a reasonable time.<br><br>" +
                "<b>6. Police and Public Safety:</b> Police enforce laws and protect citizens. Citizens must respect police authority.<br><br>" +
                "<b>7. Jury Duty:</b> Ordinary citizens decide the outcome of some cases; participation is a <b>legal duty</b>."));

            chapters.add(new Chapter("Chapter 9: Canadian Symbols (Expanded)", 
                "Symbols represent Canada’s identity, history, and values.<br><br>" +
                "<b>1. The Canadian Flag:</b> Red and white with a maple leaf, adopted in 1965. Represents unity.<br><br>" +
                "<b>2. The National Anthem:</b> 'O Canada', sung at official events and ceremonies.<br><br>" +
                "<b>3. The Crown:</b> Canada is a monarchy; the King is head of state, represented by the <b>Governor General</b>.<br><br>" +
                "<b>4. Other Important Symbols:</b><br>" +
                "• <b>The Beaver:</b> Represents hard work.<br>" +
                "• <b>The Maple Leaf:</b> Long-standing symbol of Canada.<br>" +
                "• <b>Hockey:</b> National winter sport.<br>" +
                "• <b>Lacrosse:</b> National summer sport.<br><br>" +
                "<b>5. National Colours:</b> Red and white, officially adopted in 1921."));

            chapters.add(new Chapter("Chapter 10: Canada’s Economy (Expanded)", 
                "Canada has a strong and diverse economy.<br><br>" +
                "<b>1. Economic System:</b> Mixed economy (free market + government involvement).<br><br>" +
                "<b>2. Natural Resources:</b> Rich in <b>oil and gas, forests, minerals, and fresh water</b>.<br><br>" +
                "<b>3. Major Industries:</b><br>" +
                "• <b>Primary Sector:</b> Farming, mining, fishing.<br>" +
                "• <b>Secondary Sector:</b> Manufacturing, construction.<br>" +
                "• <b>Tertiary Sector:</b> Services (banking, healthcare, retail).<br><br>" +
                "<b>4. Trade:</b> Heavily dependent on trade. Major partner: <b>United States</b>.<br><br>" +
                "<b>5. Financial System:</b> Includes banks, stock exchanges, and investment markets."));

            chapters.add(new Chapter("Chapter 11: Canada’s Regions (Expanded)", 
                "Canada is geographically vast and diverse.<br><br>" +
                "<b>1. Atlantic Provinces (NS, NB, PEI, NL):</b><br>" +
                "Features fishing industry, coastal culture, and historic settlements.<br><br>" +
                "<b>2. Central Canada (Ontario, Quebec):</b><br>" +
                "Largest population, major cities (Toronto, Montreal), finance hubs.<br><br>" +
                "<b>3. Prairie Provinces (Manitoba, Saskatchewan, Alberta):</b><br>" +
                "Known for farming (wheat, grain) and <b>oil/gas</b> (especially Alberta).<br><br>" +
                "<b>4. West Coast (British Columbia):</b><br>" +
                "Features mountains and forests, Pacific trade, and a mild climate.<br><br>" +
                "<b>5. Northern Territories (Yukon, NWT, Nunavut):</b><br>" +
                "Arctic climate, Indigenous cultures, and rich natural resources."));

            chapters.add(new Chapter("Chapter 12: Study & Review Guide (Expanded)", 
                "This chapter helps you prepare effectively for the citizenship test.<br><br>" +
                "<b>1. Key Study Tips:</b><br>" +
                "• Focus on <b>important dates</b><br>" +
                "• Understand concepts, not just memorization<br>" +
                "• Review regularly<br><br>" +
                "<b>2. Important Topics to Know:</b><br>" +
                "• Rights and responsibilities<br>" +
                "• History timeline<br>" +
                "• Government structure<br>" +
                "• Symbols and Geography<br><br>" +
                "<b>3. Practice Strategies:</b><br>" +
                "• Take mock tests<br>" +
                "• Review mistakes and study weak areas<br><br>" +
                "<b>4. Test Format:</b><br>" +
                "The test includes Multiple-choice and True/False questions."));

            chapterDao.insertAll(chapters);
        });
    }
}
