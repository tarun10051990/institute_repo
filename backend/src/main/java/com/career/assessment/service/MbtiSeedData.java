package com.career.assessment.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Canonical seed content for the MBTI assessment. Used once at startup to
 * populate the admin-manageable tables; afterwards the database is the source
 * of truth and this class is no longer consulted.
 */
public final class MbtiSeedData {

    private MbtiSeedData() {
    }

    public record SeedOption(String label, String text, String letter) {
    }

    public record SeedQuestion(int order, String dimension, String text, List<SeedOption> options) {
    }

    public record SeedProfile(String type, String nickname, String summary, String overview,
                              List<String> strengths, List<String> weaknesses, List<String> careers,
                              String relationships, String growthTips) {
    }

    private static SeedQuestion q(int order, String dimension, String text,
                                  String textA, String letterA, String textB, String letterB) {
        return new SeedQuestion(order, dimension, text,
                List.of(new SeedOption("A", textA, letterA), new SeedOption("B", textB, letterB)));
    }

    public static List<SeedQuestion> questions() {
        List<SeedQuestion> list = new ArrayList<>();

        list.add(q(1, "EI", "At a social gathering, you usually:",
                "You mingle widely and feel energized by the crowd", "E",
                "You stick to a few close friends and deeper conversations", "I"));
        list.add(q(2, "EI", "After a demanding week, you recharge best by:",
                "Going out and being around other people", "E",
                "Spending quiet time on your own", "I"));
        list.add(q(3, "EI", "In group discussions you tend to:",
                "Think out loud and speak up readily", "E",
                "Reflect first and share once you have processed", "I"));
        list.add(q(4, "EI", "You would describe yourself as more:",
                "Outgoing and expressive", "E",
                "Reserved and private", "I"));
        list.add(q(5, "EI", "When solving a problem, you prefer to:",
                "Talk it through with others", "E",
                "Work it out quietly on your own", "I"));

        list.add(q(6, "SN", "You are naturally more drawn to:",
                "Concrete facts and specific details", "S",
                "Patterns, ideas, and the big picture", "N"));
        list.add(q(7, "SN", "When learning something new, you focus on:",
                "Practical, step-by-step application", "S",
                "The underlying theory and possibilities", "N"));
        list.add(q(8, "SN", "You tend to trust more:",
                "Direct experience and what is proven", "S",
                "Your hunches, insights, and imagination", "N"));
        list.add(q(9, "SN", "You see yourself as more:",
                "Realistic and grounded in the present", "S",
                "Inventive and future-oriented", "N"));
        list.add(q(10, "SN", "You usually notice first:",
                "The specific details in front of you", "S",
                "The overall meaning and connections", "N"));

        list.add(q(11, "TF", "When making decisions, you rely more on:",
                "Logic and objective analysis", "T",
                "Personal values and how people are affected", "F"));
        list.add(q(12, "TF", "You would rather be seen as:",
                "Fair and reasonable", "T",
                "Compassionate and caring", "F"));
        list.add(q(13, "TF", "In a disagreement, you focus on:",
                "What is logically correct", "T",
                "Preserving harmony between people", "F"));
        list.add(q(14, "TF", "When giving feedback, you tend to be:",
                "Frank and direct", "T",
                "Tactful and encouraging", "F"));
        list.add(q(15, "TF", "You value more highly:",
                "Truth and competence", "T",
                "Empathy and cooperation", "F"));

        list.add(q(16, "JP", "You prefer your day-to-day life to be:",
                "Planned and organized", "J",
                "Spontaneous and flexible", "P"));
        list.add(q(17, "JP", "When facing a deadline, you usually:",
                "Finish well ahead of time", "J",
                "Work in a focused last-minute burst", "P"));
        list.add(q(18, "JP", "You feel most comfortable when things are:",
                "Decided and settled", "J",
                "Open and adaptable to change", "P"));
        list.add(q(19, "JP", "Your workspace is typically:",
                "Neat and structured", "J",
                "Casual and adaptable", "P"));
        list.add(q(20, "JP", "You approach plans by:",
                "Setting a clear schedule and sticking to it", "J",
                "Keeping your options open", "P"));

        return list;
    }

    public static List<SeedProfile> profiles() {
        List<SeedProfile> list = new ArrayList<>();

        list.add(new SeedProfile("ISTJ", "The Inspector",
                "Practical, dependable, and thorough — you value duty, order, and getting things right.",
                "ISTJs are responsible organizers who rely on facts and past experience. You honor commitments, respect structure, and take pride in doing tasks accurately and completely. Others count on you to be steady and trustworthy.",
                List.of("Reliable and responsible", "Detail-oriented and thorough", "Strong sense of duty", "Practical and logical", "Organized and methodical"),
                List.of("Can be rigid about rules", "May resist change", "Sometimes overly blunt", "Can neglect emotional needs"),
                List.of("Accountant / Auditor", "Civil / Mechanical Engineer", "Financial Analyst", "Operations Manager", "Law Enforcement Officer", "Logistics Coordinator"),
                "You show love through dependability and follow-through. You value loyalty and clear expectations, though you may need to express appreciation more openly.",
                "Practice flexibility when plans change and make room for others' feelings alongside the facts."));

        list.add(new SeedProfile("ISFJ", "The Protector",
                "Warm, conscientious, and loyal — you quietly take care of the people and duties around you.",
                "ISFJs combine practicality with deep care for others. You remember the details that matter to people, work hard behind the scenes, and create stability and comfort for those you serve.",
                List.of("Supportive and caring", "Reliable and hard-working", "Excellent memory for details", "Loyal and patient", "Practical helper"),
                List.of("Can neglect own needs", "Avoids conflict", "Reluctant to change", "May undervalue own contributions"),
                List.of("Nurse / Healthcare Worker", "Teacher", "Social Worker", "HR Specialist", "Administrative Manager", "Counselor"),
                "You are devoted and attentive to loved ones' needs. Set healthy boundaries so your giving is sustainable.",
                "Speak up for your own needs and try to see change as opportunity rather than threat."));

        list.add(new SeedProfile("INFJ", "The Advocate",
                "Insightful, principled, and compassionate — you seek meaning and want to help people grow.",
                "INFJs are visionary idealists driven by deep values. You read people well, care about the greater good, and quietly work toward a better future guided by strong personal conviction.",
                List.of("Empathetic and insightful", "Value-driven and principled", "Creative and visionary", "Committed to helping others", "Deep and reflective"),
                List.of("Prone to burnout", "Overly private", "Perfectionistic", "Sensitive to criticism"),
                List.of("Psychologist / Counselor", "Writer", "Human Rights Advocate", "Teacher / Professor", "UX Researcher", "Non-profit Leader"),
                "You seek deep, authentic connection and are a devoted partner. Guard against idealizing others or withdrawing when hurt.",
                "Share your inner world more openly and protect your energy with clear boundaries."));

        list.add(new SeedProfile("INTJ", "The Architect",
                "Strategic, independent, and analytical — you turn big ideas into long-term plans.",
                "INTJs are visionary strategists who see how systems connect and how to improve them. You think independently, set high standards, and pursue mastery and competence relentlessly.",
                List.of("Strategic and future-focused", "Independent thinker", "High standards and drive", "Analytical problem-solver", "Decisive and determined"),
                List.of("Can seem aloof or arrogant", "Impatient with inefficiency", "Overly critical", "May dismiss emotions"),
                List.of("Software Architect / Engineer", "Data Scientist", "Strategy Consultant", "Scientist / Researcher", "Investment Analyst", "Systems Designer"),
                "You value intellectual connection and honesty. Remember to express warmth and acknowledge others' feelings.",
                "Balance your drive for competence with patience, and invest in emotional communication."));

        list.add(new SeedProfile("ISTP", "The Virtuoso",
                "Observant, practical, and adaptable — you love understanding how things work and solving problems hands-on.",
                "ISTPs are cool-headed troubleshooters who thrive on action and mechanics. You stay calm under pressure, learn by doing, and enjoy figuring out efficient, practical solutions.",
                List.of("Practical problem-solver", "Calm in a crisis", "Adaptable and independent", "Hands-on and resourceful", "Logical and observant"),
                List.of("Can be reserved or detached", "Dislikes commitment", "Easily bored by routine", "May avoid long-term planning"),
                List.of("Mechanical / Aerospace Engineer", "Pilot", "Software Developer", "Emergency Technician", "Electrician / Technician", "Forensic Analyst"),
                "You show care through actions and shared activities rather than words. Practice expressing feelings verbally.",
                "Build follow-through on long-term goals and communicate your needs before withdrawing."));

        list.add(new SeedProfile("ISFP", "The Adventurer",
                "Gentle, artistic, and spontaneous — you live in the present and value beauty and authenticity.",
                "ISFPs are quiet, sensitive creators who experience the world through their senses and values. You enjoy freedom, aesthetics, and helping others in warm, practical ways.",
                List.of("Creative and artistic", "Warm and considerate", "Flexible and easygoing", "Attuned to the present", "Sensitive to beauty and people"),
                List.of("Avoids conflict", "Struggles with long-term planning", "Overly self-critical", "Dislikes rigid structure"),
                List.of("Graphic / Fashion Designer", "Chef", "Veterinarian", "Physical Therapist", "Musician / Artist", "Interior Designer"),
                "You are loving and attentive, showing affection through thoughtful gestures. Voice your needs rather than holding them in.",
                "Set longer-term goals and practice constructive handling of conflict."));

        list.add(new SeedProfile("INFP", "The Mediator",
                "Idealistic, empathetic, and imaginative — you are guided by deep values and a desire for authenticity.",
                "INFPs are thoughtful dreamers who care deeply about meaning, growth, and harmony. You are creative, compassionate, and driven to make the world align with your ideals.",
                List.of("Empathetic and caring", "Creative and imaginative", "Deeply value-driven", "Open-minded and idealistic", "Loyal to people and causes"),
                List.of("Can be impractical", "Overly idealistic", "Takes criticism personally", "May avoid difficult realities"),
                List.of("Writer / Editor", "Counselor / Therapist", "UX Designer", "Teacher", "Non-profit Worker", "Content Creator"),
                "You seek deep, meaningful bonds and are a devoted, understanding partner. Balance idealism with acceptance of imperfection.",
                "Turn ideals into concrete action and develop resilience to feedback."));

        list.add(new SeedProfile("INTP", "The Thinker",
                "Curious, logical, and inventive — you love exploring ideas and understanding how things fundamentally work.",
                "INTPs are analytical innovators driven by curiosity and a love of theory. You question assumptions, spot logical flaws, and enjoy building elegant models and solutions.",
                List.of("Analytical and logical", "Original and inventive", "Objective and open-minded", "Loves complex problems", "Independent thinker"),
                List.of("Can neglect practical details", "May procrastinate", "Sometimes insensitive", "Struggles with follow-through"),
                List.of("Software Engineer", "Data Scientist", "Research Scientist", "Mathematician", "Systems Analyst", "Economist"),
                "You value intellectual rapport and honesty over emotional display. Make an effort to acknowledge partners' feelings.",
                "Convert ideas into finished results and pay attention to everyday practicalities."));

        list.add(new SeedProfile("ESTP", "The Dynamo",
                "Energetic, pragmatic, and bold — you thrive on action, results, and living in the moment.",
                "ESTPs are quick-thinking realists who love excitement and getting things done. You read situations fast, take smart risks, and excel at solving problems on the fly.",
                List.of("Action-oriented and bold", "Great in a crisis", "Persuasive and energetic", "Practical and resourceful", "Adaptable and observant"),
                List.of("Can be impatient", "Risk-prone", "Dislikes theory and routine", "May overlook long-term impact"),
                List.of("Entrepreneur", "Sales Manager", "Paramedic", "Sports Coach", "Project Manager", "Trader"),
                "You bring energy and fun to relationships and show love through shared adventure. Slow down to attend to deeper emotional needs.",
                "Think through long-term consequences and cultivate patience for planning."));

        list.add(new SeedProfile("ESFP", "The Entertainer",
                "Enthusiastic, friendly, and spontaneous — you love people, fun, and making the moment enjoyable.",
                "ESFPs are lively, warm-hearted performers who light up a room. You live in the present, connect easily with others, and bring energy, generosity, and practical help.",
                List.of("Enthusiastic and fun-loving", "Warm and sociable", "Practical and observant", "Adaptable and generous", "Encourages others"),
                List.of("Easily bored", "Avoids conflict and planning", "Sensitive to criticism", "May act on impulse"),
                List.of("Event Planner", "Sales / Marketing", "Performer / Entertainer", "Tour Guide", "Nurse", "Hospitality Manager"),
                "You are affectionate, attentive, and generous with loved ones. Work on addressing problems rather than avoiding them.",
                "Develop long-term planning habits and sit with difficult conversations."));

        list.add(new SeedProfile("ENFP", "The Campaigner",
                "Enthusiastic, creative, and warm — you see possibilities in people and ideas everywhere.",
                "ENFPs are imaginative, people-centered enthusiasts driven by curiosity and values. You inspire others, generate ideas effortlessly, and seek meaning and connection in all you do.",
                List.of("Enthusiastic and inspiring", "Creative and curious", "Warm and empathetic", "Great communicator", "Sees potential in others"),
                List.of("Easily distracted", "Struggles with follow-through", "Overcommits", "Dislikes routine and details"),
                List.of("Marketing / PR Specialist", "Journalist", "Counselor", "Entrepreneur", "Teacher / Trainer", "Creative Director"),
                "You are passionate, supportive, and encouraging in relationships. Focus your energy so partners feel consistent follow-through.",
                "Build systems for follow-through and learn to finish what you start."));

        list.add(new SeedProfile("ENTP", "The Debater",
                "Quick, inventive, and outspoken — you love ideas, debate, and challenging the status quo.",
                "ENTPs are clever innovators who thrive on intellectual challenge and possibility. You brainstorm boldly, argue persuasively, and enjoy rethinking how things could be done.",
                List.of("Innovative and quick-witted", "Great brainstormer", "Persuasive and confident", "Adaptable and curious", "Enjoys challenges"),
                List.of("Argues for sport", "Can be insensitive", "Dislikes routine", "May not follow through"),
                List.of("Entrepreneur", "Management Consultant", "Product Manager", "Lawyer", "Marketing Strategist", "Software Architect"),
                "You bring stimulation and playfulness to relationships. Balance debate with emotional attentiveness to partners.",
                "Follow through on your best ideas and temper debate with tact."));

        list.add(new SeedProfile("ESTJ", "The Executive",
                "Organized, decisive, and dependable — you take charge and get things done efficiently.",
                "ESTJs are practical organizers and natural administrators. You value order, clear standards, and results, and you readily step up to lead, coordinate, and enforce structure.",
                List.of("Organized and efficient", "Decisive and dependable", "Strong leadership", "Practical and results-driven", "Honors commitments"),
                List.of("Can be inflexible", "Impatient", "Sometimes blunt", "May dismiss feelings"),
                List.of("Operations Manager", "Business Administrator", "Project Manager", "Financial Officer", "Military / Police Officer", "Judge"),
                "You are loyal and dependable, showing love through provision and stability. Make space for emotional expression.",
                "Practice flexibility and listen for others' feelings, not just the facts."));

        list.add(new SeedProfile("ESFJ", "The Provider",
                "Warm, sociable, and organized — you take care of others and keep communities running smoothly.",
                "ESFJs are caring, conscientious helpers who value harmony and cooperation. You attend to others' practical and emotional needs and create a warm, structured environment.",
                List.of("Caring and supportive", "Organized and reliable", "Sociable and cooperative", "Loyal and dutiful", "Attentive to others' needs"),
                List.of("Sensitive to criticism", "Avoids conflict", "Needs approval", "Can be inflexible"),
                List.of("Nurse / Healthcare Manager", "Teacher", "HR Manager", "Event Coordinator", "Customer Success Lead", "Social Worker"),
                "You are devoted, thoughtful, and nurturing with loved ones. Guard against over-relying on others' approval.",
                "Value your own needs and welcome constructive feedback without taking it personally."));

        list.add(new SeedProfile("ENFJ", "The Giver",
                "Charismatic, empathetic, and inspiring — you bring out the best in people and rally them toward shared goals.",
                "ENFJs are warm, persuasive leaders devoted to helping others grow. You read emotions well, communicate with heart, and organize people around a positive vision.",
                List.of("Charismatic and inspiring", "Empathetic and supportive", "Excellent communicator", "Organized and reliable", "Natural mentor"),
                List.of("Overextends helping others", "Sensitive to criticism", "Can be overly idealistic", "Neglects own needs"),
                List.of("Teacher / Professor", "HR / People Manager", "Counselor", "Non-profit Leader", "Public Relations", "Coach / Trainer"),
                "You are attentive, encouraging, and deeply invested in your partner's growth. Remember to care for yourself too.",
                "Set boundaries to avoid burnout and accept that you cannot fix everyone."));

        list.add(new SeedProfile("ENTJ", "The Commander",
                "Confident, strategic, and decisive — you are a natural leader who drives toward ambitious goals.",
                "ENTJs are bold, efficient leaders who see the big picture and mobilize resources to achieve it. You think strategically, decide quickly, and thrive on challenge and achievement.",
                List.of("Strong strategic leader", "Decisive and confident", "Efficient and organized", "Goal-driven", "Excellent at planning"),
                List.of("Can be domineering", "Impatient", "Sometimes insensitive", "Intolerant of inefficiency"),
                List.of("CEO / Executive", "Management Consultant", "Entrepreneur", "Investment Banker", "Lawyer", "Operations Director"),
                "You are committed and growth-oriented in relationships. Soften your directness and make room for others' emotions.",
                "Practice patience and empathy, and value people beyond their productivity."));

        return list;
    }
}
