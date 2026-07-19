USE career_assessment_db;

-- Insert 5 assessment categories (Mindler's 5 dimensions)
INSERT INTO categories (name, code, description, icon, display_order, time_limit_minutes, total_questions) VALUES
('Orientation Style', 'ORIENTATION', 'Discover your preferred work environment, work style, and how you approach tasks. This assessment reveals whether you thrive in structured or flexible environments, prefer working independently or in teams.', 'compass', 1, 12, 15),
('Interest', 'INTEREST', 'Identify your core career interests across different professional domains. Based on the RIASEC model, this assessment maps your interests to Realistic, Investigative, Artistic, Social, Enterprising, and Conventional career fields.', 'star', 2, 15, 15),
('Personality', 'PERSONALITY', 'Understand your personality traits and how they influence your career choices. This assessment evaluates key dimensions including openness, conscientiousness, extraversion, agreeableness, and emotional stability.', 'user', 3, 15, 15),
('Aptitude', 'APTITUDE', 'Evaluate your cognitive abilities across numerical reasoning, verbal reasoning, logical thinking, and spatial awareness. Understand your natural strengths and areas for development.', 'brain', 4, 20, 15),
('Emotional Quotient', 'EQ', 'Measure your emotional intelligence including self-awareness, self-regulation, motivation, empathy, and social skills. EQ is a critical factor in career success and workplace relationships.', 'heart', 5, 12, 15);

-- ============================================
-- CATEGORY 1: ORIENTATION STYLE (15 questions)
-- ============================================

INSERT INTO questions (category_id, question_text, question_type, difficulty_level, display_order) VALUES
(1, 'When working on a project, I prefer to:', 'SINGLE_CHOICE', 'EASY', 1),
(1, 'My ideal work environment would be:', 'SINGLE_CHOICE', 'EASY', 2),
(1, 'When facing a challenging problem, I usually:', 'SINGLE_CHOICE', 'MEDIUM', 3),
(1, 'I am most productive when:', 'SINGLE_CHOICE', 'EASY', 4),
(1, 'In a team setting, I naturally take on the role of:', 'SINGLE_CHOICE', 'MEDIUM', 5),
(1, 'I prefer tasks that are:', 'SINGLE_CHOICE', 'EASY', 6),
(1, 'When learning something new, I prefer to:', 'SINGLE_CHOICE', 'MEDIUM', 7),
(1, 'My approach to deadlines is:', 'SINGLE_CHOICE', 'EASY', 8),
(1, 'I feel most satisfied when my work involves:', 'SINGLE_CHOICE', 'MEDIUM', 9),
(1, 'When given a choice, I would rather:', 'SINGLE_CHOICE', 'EASY', 10),
(1, 'I handle change and uncertainty by:', 'SINGLE_CHOICE', 'MEDIUM', 11),
(1, 'My communication style is best described as:', 'SINGLE_CHOICE', 'EASY', 12),
(1, 'I prefer a career that offers:', 'SINGLE_CHOICE', 'MEDIUM', 13),
(1, 'When making decisions, I rely more on:', 'SINGLE_CHOICE', 'EASY', 14),
(1, 'My ideal work schedule would be:', 'SINGLE_CHOICE', 'EASY', 15);

-- Orientation Style Q1 options
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(1, 'Plan everything in detail before starting', 'A', 4, 'STRUCTURED', 1),
(1, 'Start working and figure things out as I go', 'B', 3, 'FLEXIBLE', 2),
(1, 'Discuss the approach with teammates first', 'C', 5, 'COLLABORATIVE', 3),
(1, 'Research best practices and follow proven methods', 'D', 4, 'ANALYTICAL', 4);

-- Orientation Style Q2 options
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(2, 'A quiet, private office where I can concentrate', 'A', 4, 'INDEPENDENT', 1),
(2, 'An open, collaborative workspace with team interaction', 'B', 5, 'COLLABORATIVE', 2),
(2, 'A flexible space where I can move between areas', 'C', 3, 'FLEXIBLE', 3),
(2, 'An outdoor or field-based environment', 'D', 4, 'HANDS_ON', 4);

-- Orientation Style Q3 options
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(3, 'Break it down into smaller, manageable parts', 'A', 5, 'ANALYTICAL', 1),
(3, 'Brainstorm creative solutions with others', 'B', 4, 'CREATIVE', 2),
(3, 'Look for similar problems that have been solved before', 'C', 3, 'PRACTICAL', 3),
(3, 'Trust my intuition and try different approaches', 'D', 4, 'INTUITIVE', 4);

-- Orientation Style Q4 options
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(4, 'I have a clear set of goals and a structured plan', 'A', 5, 'STRUCTURED', 1),
(4, 'I have the freedom to work at my own pace', 'B', 4, 'INDEPENDENT', 2),
(4, 'I am working with a motivated team', 'C', 4, 'COLLABORATIVE', 3),
(4, 'I am working on something that interests me deeply', 'D', 3, 'PASSIONATE', 4);

-- Orientation Style Q5 options
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(5, 'The leader who directs and organizes', 'A', 5, 'LEADERSHIP', 1),
(5, 'The contributor who delivers quality work', 'B', 4, 'INDEPENDENT', 2),
(5, 'The mediator who ensures everyone works well together', 'C', 4, 'COLLABORATIVE', 3),
(5, 'The innovator who comes up with new ideas', 'D', 3, 'CREATIVE', 4);

-- Orientation Style Q6 options
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(6, 'Well-defined with clear instructions', 'A', 4, 'STRUCTURED', 1),
(6, 'Open-ended with room for creativity', 'B', 5, 'CREATIVE', 2),
(6, 'Hands-on and practical', 'C', 4, 'HANDS_ON', 3),
(6, 'Analytical and require problem-solving', 'D', 3, 'ANALYTICAL', 4);

-- Orientation Style Q7 options
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(7, 'Read books, articles, or watch tutorials', 'A', 4, 'ANALYTICAL', 1),
(7, 'Practice hands-on through trial and error', 'B', 5, 'HANDS_ON', 2),
(7, 'Attend classes or learn from an instructor', 'C', 3, 'STRUCTURED', 3),
(7, 'Discuss and learn collaboratively with peers', 'D', 4, 'COLLABORATIVE', 4);

-- Orientation Style Q8 options
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(8, 'I always finish well before the deadline', 'A', 5, 'STRUCTURED', 1),
(8, 'I work best under pressure close to the deadline', 'B', 3, 'FLEXIBLE', 2),
(8, 'I set my own milestones and track progress regularly', 'C', 4, 'INDEPENDENT', 3),
(8, 'I collaborate with others to ensure timely completion', 'D', 4, 'COLLABORATIVE', 4);

-- Orientation Style Q9 options
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(9, 'Creating something new and innovative', 'A', 5, 'CREATIVE', 1),
(9, 'Helping others and making a positive impact', 'B', 4, 'SOCIAL', 2),
(9, 'Analyzing data and solving complex problems', 'C', 4, 'ANALYTICAL', 3),
(9, 'Organizing and managing processes efficiently', 'D', 3, 'STRUCTURED', 4);

-- Orientation Style Q10 options
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(10, 'Work on a single project with deep focus', 'A', 4, 'INDEPENDENT', 1),
(10, 'Juggle multiple projects simultaneously', 'B', 3, 'FLEXIBLE', 2),
(10, 'Lead a team on an important initiative', 'C', 5, 'LEADERSHIP', 3),
(10, 'Research and explore new possibilities', 'D', 4, 'ANALYTICAL', 4);

-- Orientation Style Q11 options
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(11, 'Creating a new plan and adapting quickly', 'A', 5, 'FLEXIBLE', 1),
(11, 'Sticking to what I know and being cautious', 'B', 3, 'STRUCTURED', 2),
(11, 'Seeking advice and input from others', 'C', 4, 'COLLABORATIVE', 3),
(11, 'Analyzing the situation thoroughly before acting', 'D', 4, 'ANALYTICAL', 4);

-- Orientation Style Q12 options
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(12, 'Direct and to the point', 'A', 4, 'INDEPENDENT', 1),
(12, 'Warm, friendly, and supportive', 'B', 5, 'SOCIAL', 2),
(12, 'Detailed and precise', 'C', 3, 'ANALYTICAL', 3),
(12, 'Persuasive and enthusiastic', 'D', 4, 'LEADERSHIP', 4);

-- Orientation Style Q13 options
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(13, 'Stability and predictable growth', 'A', 3, 'STRUCTURED', 1),
(13, 'Variety and new experiences', 'B', 5, 'FLEXIBLE', 2),
(13, 'Making a difference in people lives', 'C', 4, 'SOCIAL', 3),
(13, 'High earning potential and recognition', 'D', 4, 'LEADERSHIP', 4);

-- Orientation Style Q14 options
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(14, 'Facts, data, and logical analysis', 'A', 5, 'ANALYTICAL', 1),
(14, 'Gut feeling and past experience', 'B', 3, 'INTUITIVE', 2),
(14, 'Input and opinions from trusted people', 'C', 4, 'COLLABORATIVE', 3),
(14, 'A mix of analysis and intuition', 'D', 4, 'FLEXIBLE', 4);

-- Orientation Style Q15 options
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(15, 'Fixed 9-to-5 schedule with weekends off', 'A', 3, 'STRUCTURED', 1),
(15, 'Flexible hours where I manage my own time', 'B', 5, 'INDEPENDENT', 2),
(15, 'Varies based on projects and deadlines', 'C', 4, 'FLEXIBLE', 3),
(15, 'Shift-based or field work with different routines', 'D', 4, 'HANDS_ON', 4);

-- ============================================
-- CATEGORY 2: INTEREST (15 questions)
-- ============================================

INSERT INTO questions (category_id, question_text, question_type, difficulty_level, display_order) VALUES
(2, 'Which activity appeals to you the most?', 'SINGLE_CHOICE', 'EASY', 1),
(2, 'In your free time, you would most enjoy:', 'SINGLE_CHOICE', 'EASY', 2),
(2, 'Which subject do you find most interesting?', 'SINGLE_CHOICE', 'EASY', 3),
(2, 'Which career environment excites you the most?', 'SINGLE_CHOICE', 'MEDIUM', 4),
(2, 'What type of problems do you enjoy solving?', 'SINGLE_CHOICE', 'MEDIUM', 5),
(2, 'Which project would you volunteer for?', 'SINGLE_CHOICE', 'MEDIUM', 6),
(2, 'What kind of books or content do you consume most?', 'SINGLE_CHOICE', 'EASY', 7),
(2, 'Which role model inspires you the most?', 'SINGLE_CHOICE', 'MEDIUM', 8),
(2, 'If you could start a business, it would be in:', 'SINGLE_CHOICE', 'MEDIUM', 9),
(2, 'Which skill would you most like to develop?', 'SINGLE_CHOICE', 'EASY', 10),
(2, 'What type of work achievement would make you proudest?', 'SINGLE_CHOICE', 'MEDIUM', 11),
(2, 'Which workshop would you attend?', 'SINGLE_CHOICE', 'EASY', 12),
(2, 'What kind of impact do you want your career to have?', 'SINGLE_CHOICE', 'MEDIUM', 13),
(2, 'Which tool or instrument would you most like to master?', 'SINGLE_CHOICE', 'EASY', 14),
(2, 'In a group project at school, you prefer to:', 'SINGLE_CHOICE', 'EASY', 15);

-- Interest Q1 (id=16)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(16, 'Building or repairing machines and equipment', 'A', 5, 'REALISTIC', 1),
(16, 'Conducting scientific experiments or research', 'B', 4, 'INVESTIGATIVE', 2),
(16, 'Creating art, music, or writing stories', 'C', 4, 'ARTISTIC', 3),
(16, 'Teaching, counseling, or helping people', 'D', 3, 'SOCIAL', 4);

-- Interest Q2 (id=17)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(17, 'Working on a DIY project or gardening', 'A', 4, 'REALISTIC', 1),
(17, 'Reading about scientific discoveries', 'B', 5, 'INVESTIGATIVE', 2),
(17, 'Painting, drawing, or playing music', 'C', 4, 'ARTISTIC', 3),
(17, 'Organizing a community event or volunteering', 'D', 3, 'SOCIAL', 4);

-- Interest Q3 (id=18)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(18, 'Mathematics and Physics', 'A', 4, 'REALISTIC', 1),
(18, 'Biology and Chemistry', 'B', 5, 'INVESTIGATIVE', 2),
(18, 'Literature and Arts', 'C', 4, 'ARTISTIC', 3),
(18, 'Psychology and Social Sciences', 'D', 3, 'SOCIAL', 4);

-- Interest Q4 (id=19)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(19, 'A tech lab or engineering workshop', 'A', 5, 'REALISTIC', 1),
(19, 'A research laboratory or university', 'B', 4, 'INVESTIGATIVE', 2),
(19, 'A creative studio or media house', 'C', 4, 'ARTISTIC', 3),
(19, 'A startup or business office', 'D', 3, 'ENTERPRISING', 4);

-- Interest Q5 (id=20)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(20, 'Technical or mechanical problems', 'A', 5, 'REALISTIC', 1),
(20, 'Scientific or theoretical puzzles', 'B', 4, 'INVESTIGATIVE', 2),
(20, 'Design or aesthetic challenges', 'C', 3, 'ARTISTIC', 3),
(20, 'Business strategy and growth challenges', 'D', 4, 'ENTERPRISING', 4);

-- Interest Q6 (id=21)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(21, 'Building a website or app from scratch', 'A', 5, 'REALISTIC', 1),
(21, 'Analyzing data for a research study', 'B', 4, 'INVESTIGATIVE', 2),
(21, 'Designing marketing materials or branding', 'C', 4, 'ARTISTIC', 3),
(21, 'Managing finances and budgets', 'D', 3, 'CONVENTIONAL', 4);

-- Interest Q7 (id=22)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(22, 'Technology and engineering magazines', 'A', 4, 'REALISTIC', 1),
(22, 'Science journals and documentaries', 'B', 5, 'INVESTIGATIVE', 2),
(22, 'Fiction, poetry, or art books', 'C', 4, 'ARTISTIC', 3),
(22, 'Business and entrepreneurship content', 'D', 3, 'ENTERPRISING', 4);

-- Interest Q8 (id=23)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(23, 'Engineers and inventors (like Elon Musk)', 'A', 5, 'REALISTIC', 1),
(23, 'Scientists and researchers (like Marie Curie)', 'B', 4, 'INVESTIGATIVE', 2),
(23, 'Artists and creators (like AR Rahman)', 'C', 4, 'ARTISTIC', 3),
(23, 'Social leaders and activists (like Malala)', 'D', 3, 'SOCIAL', 4);

-- Interest Q9 (id=24)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(24, 'Technology or manufacturing', 'A', 5, 'REALISTIC', 1),
(24, 'Healthcare or biotech', 'B', 4, 'INVESTIGATIVE', 2),
(24, 'Media, design, or entertainment', 'C', 4, 'ARTISTIC', 3),
(24, 'Finance, consulting, or management', 'D', 3, 'ENTERPRISING', 4);

-- Interest Q10 (id=25)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(25, 'Coding and software development', 'A', 5, 'REALISTIC', 1),
(25, 'Data analysis and statistics', 'B', 4, 'INVESTIGATIVE', 2),
(25, 'Graphic design or video editing', 'C', 4, 'ARTISTIC', 3),
(25, 'Public speaking and negotiation', 'D', 3, 'ENTERPRISING', 4);

-- Interest Q11 (id=26)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(26, 'Building something tangible that people use', 'A', 5, 'REALISTIC', 1),
(26, 'Making a scientific breakthrough', 'B', 4, 'INVESTIGATIVE', 2),
(26, 'Creating art that moves and inspires people', 'C', 4, 'ARTISTIC', 3),
(26, 'Building a successful organization', 'D', 3, 'ENTERPRISING', 4);

-- Interest Q12 (id=27)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(27, 'Robotics or 3D printing workshop', 'A', 5, 'REALISTIC', 1),
(27, 'Science fair or innovation challenge', 'B', 4, 'INVESTIGATIVE', 2),
(27, 'Photography or filmmaking masterclass', 'C', 4, 'ARTISTIC', 3),
(27, 'Debate competition or Model UN', 'D', 3, 'SOCIAL', 4);

-- Interest Q13 (id=28)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(28, 'Advance technology and innovation', 'A', 5, 'REALISTIC', 1),
(28, 'Expand human knowledge and understanding', 'B', 4, 'INVESTIGATIVE', 2),
(28, 'Bring beauty and creativity to the world', 'C', 4, 'ARTISTIC', 3),
(28, 'Help and empower communities', 'D', 3, 'SOCIAL', 4);

-- Interest Q14 (id=29)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(29, 'Programming languages or hardware tools', 'A', 5, 'REALISTIC', 1),
(29, 'Microscope or lab instruments', 'B', 4, 'INVESTIGATIVE', 2),
(29, 'Musical instrument or design software', 'C', 4, 'ARTISTIC', 3),
(29, 'Spreadsheets and organizational tools', 'D', 3, 'CONVENTIONAL', 4);

-- Interest Q15 (id=30)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(30, 'Handle the technical aspects and building', 'A', 5, 'REALISTIC', 1),
(30, 'Research and gather information', 'B', 4, 'INVESTIGATIVE', 2),
(30, 'Create the presentation or visual materials', 'C', 4, 'ARTISTIC', 3),
(30, 'Coordinate the team and present to others', 'D', 3, 'ENTERPRISING', 4);

-- ============================================
-- CATEGORY 3: PERSONALITY (15 questions)
-- ============================================

INSERT INTO questions (category_id, question_text, question_type, difficulty_level, display_order) VALUES
(3, 'At a social gathering, you usually:', 'SINGLE_CHOICE', 'EASY', 1),
(3, 'When planning a trip, you prefer to:', 'SINGLE_CHOICE', 'EASY', 2),
(3, 'How do you handle criticism?', 'SINGLE_CHOICE', 'MEDIUM', 3),
(3, 'When working on a group assignment:', 'SINGLE_CHOICE', 'EASY', 4),
(3, 'Your approach to rules and regulations is:', 'SINGLE_CHOICE', 'MEDIUM', 5),
(3, 'How do you recharge after a long day?', 'SINGLE_CHOICE', 'EASY', 6),
(3, 'When faced with a moral dilemma, you:', 'SINGLE_CHOICE', 'MEDIUM', 7),
(3, 'Your friends would describe you as:', 'SINGLE_CHOICE', 'EASY', 8),
(3, 'How do you handle stress?', 'SINGLE_CHOICE', 'MEDIUM', 9),
(3, 'When trying something new, you feel:', 'SINGLE_CHOICE', 'EASY', 10),
(3, 'In a debate or argument, you tend to:', 'SINGLE_CHOICE', 'MEDIUM', 11),
(3, 'How organized are you in daily life?', 'SINGLE_CHOICE', 'EASY', 12),
(3, 'When someone shares their problems with you:', 'SINGLE_CHOICE', 'MEDIUM', 13),
(3, 'Your attitude towards new ideas is:', 'SINGLE_CHOICE', 'EASY', 14),
(3, 'How do you approach long-term goals?', 'SINGLE_CHOICE', 'MEDIUM', 15);

-- Personality Q1 (id=31)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(31, 'Enjoy meeting new people and being the center of attention', 'A', 5, 'EXTRAVERSION', 1),
(31, 'Prefer to stay with a small group of close friends', 'B', 3, 'INTROVERSION', 2),
(31, 'Observe and listen before joining conversations', 'C', 4, 'INTROVERSION', 3),
(31, 'Actively network and exchange ideas with many people', 'D', 4, 'EXTRAVERSION', 4);

-- Personality Q2 (id=32)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(32, 'Have every detail planned and scheduled', 'A', 5, 'CONSCIENTIOUSNESS', 1),
(32, 'Go with the flow and be spontaneous', 'B', 3, 'OPENNESS', 2),
(32, 'Plan the major things but leave room for flexibility', 'C', 4, 'BALANCED', 3),
(32, 'Let someone else plan while I enjoy the experience', 'D', 3, 'AGREEABLENESS', 4);

-- Personality Q3 (id=33)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(33, 'Take it constructively and work on improvement', 'A', 5, 'CONSCIENTIOUSNESS', 1),
(33, 'Feel hurt but try not to show it', 'B', 3, 'EMOTIONAL_SENSITIVITY', 2),
(33, 'Defend my position with evidence', 'C', 4, 'EXTRAVERSION', 3),
(33, 'Reflect deeply on whether the feedback is valid', 'D', 4, 'OPENNESS', 4);

-- Personality Q4 (id=34)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(34, 'I take charge and delegate tasks', 'A', 5, 'EXTRAVERSION', 1),
(34, 'I contribute my part quietly and efficiently', 'B', 4, 'CONSCIENTIOUSNESS', 2),
(34, 'I make sure everyone feels included and heard', 'C', 4, 'AGREEABLENESS', 3),
(34, 'I come up with creative ideas and solutions', 'D', 3, 'OPENNESS', 4);

-- Personality Q5 (id=35)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(35, 'I follow them strictly as they exist for good reason', 'A', 5, 'CONSCIENTIOUSNESS', 1),
(35, 'I follow them but question those that seem unnecessary', 'B', 4, 'OPENNESS', 2),
(35, 'I bend them if the situation calls for it', 'C', 3, 'OPENNESS', 3),
(35, 'I believe in creating my own rules', 'D', 3, 'EXTRAVERSION', 4);

-- Personality Q6 (id=36)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(36, 'Spending time alone reading, gaming, or relaxing', 'A', 4, 'INTROVERSION', 1),
(36, 'Going out with friends or attending events', 'B', 5, 'EXTRAVERSION', 2),
(36, 'Engaging in a hobby like painting, cooking, or music', 'C', 4, 'OPENNESS', 3),
(36, 'Exercising, meditating, or being in nature', 'D', 3, 'CONSCIENTIOUSNESS', 4);

-- Personality Q7 (id=37)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(37, 'Follow your principles strictly, regardless of consequences', 'A', 5, 'CONSCIENTIOUSNESS', 1),
(37, 'Consider the feelings and needs of everyone involved', 'B', 4, 'AGREEABLENESS', 2),
(37, 'Weigh the pros and cons logically', 'C', 4, 'OPENNESS', 3),
(37, 'Trust your gut and act on instinct', 'D', 3, 'EXTRAVERSION', 4);

-- Personality Q8 (id=38)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(38, 'Outgoing, energetic, and fun to be around', 'A', 5, 'EXTRAVERSION', 1),
(38, 'Reliable, responsible, and hardworking', 'B', 4, 'CONSCIENTIOUSNESS', 2),
(38, 'Kind, caring, and always ready to help', 'C', 4, 'AGREEABLENESS', 3),
(38, 'Thoughtful, creative, and imaginative', 'D', 3, 'OPENNESS', 4);

-- Personality Q9 (id=39)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(39, 'Create a plan to address the source of stress', 'A', 5, 'CONSCIENTIOUSNESS', 1),
(39, 'Talk to friends or family for support', 'B', 4, 'AGREEABLENESS', 2),
(39, 'Distract myself with activities I enjoy', 'C', 3, 'OPENNESS', 3),
(39, 'Push through it with determination', 'D', 4, 'EXTRAVERSION', 4);

-- Personality Q10 (id=40)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(40, 'Excited and eager to explore', 'A', 5, 'OPENNESS', 1),
(40, 'A bit anxious but willing to try', 'B', 3, 'EMOTIONAL_SENSITIVITY', 2),
(40, 'Cautious and prefer to be well-prepared', 'C', 4, 'CONSCIENTIOUSNESS', 3),
(40, 'Confident and ready to jump in', 'D', 4, 'EXTRAVERSION', 4);

-- Personality Q11 (id=41)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(41, 'Present your case passionately and convincingly', 'A', 5, 'EXTRAVERSION', 1),
(41, 'Listen carefully and try to find common ground', 'B', 4, 'AGREEABLENESS', 2),
(41, 'Use logic and evidence to support your position', 'C', 4, 'CONSCIENTIOUSNESS', 3),
(41, 'Avoid confrontation and agree to disagree', 'D', 3, 'INTROVERSION', 4);

-- Personality Q12 (id=42)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(42, 'Very organized with everything in its place', 'A', 5, 'CONSCIENTIOUSNESS', 1),
(42, 'Somewhat organized with a flexible system', 'B', 4, 'BALANCED', 2),
(42, 'Creative chaos that somehow works for me', 'C', 3, 'OPENNESS', 3),
(42, 'I organize when I need to but am generally relaxed', 'D', 3, 'AGREEABLENESS', 4);

-- Personality Q13 (id=43)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(43, 'I listen empathetically and offer emotional support', 'A', 5, 'AGREEABLENESS', 1),
(43, 'I try to help by suggesting practical solutions', 'B', 4, 'CONSCIENTIOUSNESS', 2),
(43, 'I share my own similar experiences to relate', 'C', 3, 'EXTRAVERSION', 3),
(43, 'I give them space and check on them later', 'D', 4, 'INTROVERSION', 4);

-- Personality Q14 (id=44)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(44, 'I love exploring and embracing new ideas', 'A', 5, 'OPENNESS', 1),
(44, 'I evaluate them carefully before accepting', 'B', 4, 'CONSCIENTIOUSNESS', 2),
(44, 'I prefer proven and tested approaches', 'C', 3, 'CONSCIENTIOUSNESS', 3),
(44, 'I adapt to new ideas if they make logical sense', 'D', 4, 'BALANCED', 4);

-- Personality Q15 (id=45)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(45, 'I set clear milestones and work towards them systematically', 'A', 5, 'CONSCIENTIOUSNESS', 1),
(45, 'I have a general direction but stay open to changes', 'B', 4, 'OPENNESS', 2),
(45, 'I focus on short-term wins that build towards the big goal', 'C', 4, 'EXTRAVERSION', 3),
(45, 'I dream big but sometimes struggle with follow-through', 'D', 3, 'OPENNESS', 4);

-- ============================================
-- CATEGORY 4: APTITUDE (15 questions)
-- ============================================

INSERT INTO questions (category_id, question_text, question_type, difficulty_level, display_order) VALUES
(4, 'If 3x + 7 = 22, what is the value of x?', 'SINGLE_CHOICE', 'EASY', 1),
(4, 'Which word is most similar in meaning to "ELOQUENT"?', 'SINGLE_CHOICE', 'MEDIUM', 2),
(4, 'What comes next in the series: 2, 6, 18, 54, ?', 'SINGLE_CHOICE', 'EASY', 3),
(4, 'A train travels 120 km in 2 hours. What is its speed in m/s?', 'SINGLE_CHOICE', 'MEDIUM', 4),
(4, 'Choose the word that is opposite in meaning to "BENEVOLENT":', 'SINGLE_CHOICE', 'MEDIUM', 5),
(4, 'If all roses are flowers, and some flowers fade quickly, which statement must be true?', 'SINGLE_CHOICE', 'MEDIUM', 6),
(4, 'A rectangle has a length of 12 cm and width of 8 cm. What is the length of its diagonal?', 'SINGLE_CHOICE', 'MEDIUM', 7),
(4, 'Complete the analogy: Book is to Reading as Fork is to:', 'SINGLE_CHOICE', 'EASY', 8),
(4, 'If the price of an item is increased by 20% and then decreased by 20%, the net change is:', 'SINGLE_CHOICE', 'HARD', 9),
(4, 'Which figure would complete the pattern? In a 3x3 grid, each row has a circle, triangle, and square. The missing shape in row 3 is:', 'SINGLE_CHOICE', 'MEDIUM', 10),
(4, 'Find the odd one out: Mercury, Venus, Mars, Moon, Jupiter', 'SINGLE_CHOICE', 'EASY', 11),
(4, 'If 5 workers can build a wall in 10 days, how many days will 10 workers take?', 'SINGLE_CHOICE', 'EASY', 12),
(4, 'Choose the correct spelling:', 'SINGLE_CHOICE', 'EASY', 13),
(4, 'What is 15% of 240?', 'SINGLE_CHOICE', 'EASY', 14),
(4, 'In a certain code, COMPUTER is written as RFUVQNPC. How is MEDICINE written in that code?', 'SINGLE_CHOICE', 'HARD', 15);

-- Aptitude Q1 (id=46)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(46, '3', 'A', 0, 'NUMERICAL', 1),
(46, '5', 'B', 5, 'NUMERICAL', 2),
(46, '7', 'C', 0, 'NUMERICAL', 3),
(46, '4', 'D', 0, 'NUMERICAL', 4);

-- Aptitude Q2 (id=47)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(47, 'Silent', 'A', 0, 'VERBAL', 1),
(47, 'Articulate', 'B', 5, 'VERBAL', 2),
(47, 'Confused', 'C', 0, 'VERBAL', 3),
(47, 'Loud', 'D', 0, 'VERBAL', 4);

-- Aptitude Q3 (id=48)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(48, '108', 'A', 0, 'LOGICAL', 1),
(48, '162', 'B', 5, 'LOGICAL', 2),
(48, '148', 'C', 0, 'LOGICAL', 3),
(48, '180', 'D', 0, 'LOGICAL', 4);

-- Aptitude Q4 (id=49)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(49, '60 m/s', 'A', 0, 'NUMERICAL', 1),
(49, '16.67 m/s', 'B', 5, 'NUMERICAL', 2),
(49, '33.33 m/s', 'C', 0, 'NUMERICAL', 3),
(49, '20 m/s', 'D', 0, 'NUMERICAL', 4);

-- Aptitude Q5 (id=50)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(50, 'Generous', 'A', 0, 'VERBAL', 1),
(50, 'Malevolent', 'B', 5, 'VERBAL', 2),
(50, 'Friendly', 'C', 0, 'VERBAL', 3),
(50, 'Careless', 'D', 0, 'VERBAL', 4);

-- Aptitude Q6 (id=51)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(51, 'All roses fade quickly', 'A', 0, 'LOGICAL', 1),
(51, 'Some roses may fade quickly', 'B', 5, 'LOGICAL', 2),
(51, 'No roses fade quickly', 'C', 0, 'LOGICAL', 3),
(51, 'All flowers are roses', 'D', 0, 'LOGICAL', 4);

-- Aptitude Q7 (id=52)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(52, '10 cm', 'A', 0, 'SPATIAL', 1),
(52, '4 root 13 cm (approx 14.42)', 'B', 5, 'SPATIAL', 2),
(52, '16 cm', 'C', 0, 'SPATIAL', 3),
(52, '20 cm', 'D', 0, 'SPATIAL', 4);

-- Aptitude Q8 (id=53)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(53, 'Cooking', 'A', 0, 'VERBAL', 1),
(53, 'Eating', 'B', 5, 'VERBAL', 2),
(53, 'Cutting', 'C', 0, 'VERBAL', 3),
(53, 'Washing', 'D', 0, 'VERBAL', 4);

-- Aptitude Q9 (id=54)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(54, 'No change', 'A', 0, 'NUMERICAL', 1),
(54, '4% decrease', 'B', 5, 'NUMERICAL', 2),
(54, '4% increase', 'C', 0, 'NUMERICAL', 3),
(54, '2% decrease', 'D', 0, 'NUMERICAL', 4);

-- Aptitude Q10 (id=55)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(55, 'Circle', 'A', 0, 'SPATIAL', 1),
(55, 'Triangle', 'B', 5, 'SPATIAL', 2),
(55, 'Square', 'C', 0, 'SPATIAL', 3),
(55, 'Pentagon', 'D', 0, 'SPATIAL', 4);

-- Aptitude Q11 (id=56)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(56, 'Mercury', 'A', 0, 'LOGICAL', 1),
(56, 'Moon', 'B', 5, 'LOGICAL', 2),
(56, 'Venus', 'C', 0, 'LOGICAL', 3),
(56, 'Jupiter', 'D', 0, 'LOGICAL', 4);

-- Aptitude Q12 (id=57)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(57, '5 days', 'A', 5, 'NUMERICAL', 1),
(57, '10 days', 'B', 0, 'NUMERICAL', 2),
(57, '15 days', 'C', 0, 'NUMERICAL', 3),
(57, '20 days', 'D', 0, 'NUMERICAL', 4);

-- Aptitude Q13 (id=58)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(58, 'Accomodation', 'A', 0, 'VERBAL', 1),
(58, 'Accommodation', 'B', 5, 'VERBAL', 2),
(58, 'Acomodation', 'C', 0, 'VERBAL', 3),
(58, 'Accommadation', 'D', 0, 'VERBAL', 4);

-- Aptitude Q14 (id=59)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(59, '32', 'A', 0, 'NUMERICAL', 1),
(59, '36', 'B', 5, 'NUMERICAL', 2),
(59, '38', 'C', 0, 'NUMERICAL', 3),
(59, '40', 'D', 0, 'NUMERICAL', 4);

-- Aptitude Q15 (id=60)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(60, 'EDJDIDOF', 'A', 0, 'LOGICAL', 1),
(60, 'FOJEJDOF', 'B', 0, 'LOGICAL', 2),
(60, 'GFEJDJOF', 'C', 0, 'LOGICAL', 3),
(60, 'EFICJDNE', 'D', 5, 'LOGICAL', 4);

-- ============================================
-- CATEGORY 5: EMOTIONAL QUOTIENT (15 questions)
-- ============================================

INSERT INTO questions (category_id, question_text, question_type, difficulty_level, display_order) VALUES
(5, 'When you feel angry, you usually:', 'SINGLE_CHOICE', 'EASY', 1),
(5, 'How well do you understand your own emotions?', 'SINGLE_CHOICE', 'EASY', 2),
(5, 'When a friend is upset, you typically:', 'SINGLE_CHOICE', 'EASY', 3),
(5, 'How do you handle failure or setbacks?', 'SINGLE_CHOICE', 'MEDIUM', 4),
(5, 'In a conflict with someone, you usually:', 'SINGLE_CHOICE', 'MEDIUM', 5),
(5, 'How do you motivate yourself for difficult tasks?', 'SINGLE_CHOICE', 'MEDIUM', 6),
(5, 'When receiving unexpected bad news, you:', 'SINGLE_CHOICE', 'MEDIUM', 7),
(5, 'How do you handle peer pressure?', 'SINGLE_CHOICE', 'MEDIUM', 8),
(5, 'When working with someone who has a very different personality:', 'SINGLE_CHOICE', 'MEDIUM', 9),
(5, 'How do you celebrate others successes?', 'SINGLE_CHOICE', 'EASY', 10),
(5, 'When you make a mistake that affects others:', 'SINGLE_CHOICE', 'MEDIUM', 11),
(5, 'How comfortable are you expressing your emotions?', 'SINGLE_CHOICE', 'EASY', 12),
(5, 'When someone criticizes your work unfairly:', 'SINGLE_CHOICE', 'MEDIUM', 13),
(5, 'How do you handle situations where you feel left out?', 'SINGLE_CHOICE', 'MEDIUM', 14),
(5, 'When you sense tension in a group:', 'SINGLE_CHOICE', 'MEDIUM', 15);

-- EQ Q1 (id=61)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(61, 'Take a pause and try to understand why I am angry', 'A', 5, 'SELF_AWARENESS', 1),
(61, 'Express my anger immediately', 'B', 2, 'SELF_REGULATION', 2),
(61, 'Suppress my feelings and move on', 'C', 3, 'SELF_REGULATION', 3),
(61, 'Talk to someone about how I feel', 'D', 4, 'SOCIAL_SKILLS', 4);

-- EQ Q2 (id=62)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(62, 'I can clearly identify and name what I am feeling', 'A', 5, 'SELF_AWARENESS', 1),
(62, 'I sometimes know what I feel but cannot always explain it', 'B', 3, 'SELF_AWARENESS', 2),
(62, 'I often feel confused about my emotions', 'C', 2, 'SELF_AWARENESS', 3),
(62, 'I rarely pay attention to my emotions', 'D', 1, 'SELF_AWARENESS', 4);

-- EQ Q3 (id=63)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(63, 'Listen actively and offer comfort without judgment', 'A', 5, 'EMPATHY', 1),
(63, 'Try to fix their problem immediately', 'B', 3, 'SOCIAL_SKILLS', 2),
(63, 'Share a similar experience to show I understand', 'C', 4, 'EMPATHY', 3),
(63, 'Give them space and wait for them to come to me', 'D', 3, 'SELF_REGULATION', 4);

-- EQ Q4 (id=64)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(64, 'Analyze what went wrong and learn from it', 'A', 5, 'MOTIVATION', 1),
(64, 'Feel disappointed but bounce back quickly', 'B', 4, 'SELF_REGULATION', 2),
(64, 'Dwell on it for a while before moving on', 'C', 2, 'SELF_REGULATION', 3),
(64, 'Seek encouragement and support from others', 'D', 3, 'SOCIAL_SKILLS', 4);

-- EQ Q5 (id=65)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(65, 'Try to understand their perspective and find a compromise', 'A', 5, 'EMPATHY', 1),
(65, 'Stand firm on my position and try to convince them', 'B', 2, 'SELF_REGULATION', 2),
(65, 'Avoid the conflict altogether', 'C', 2, 'SELF_REGULATION', 3),
(65, 'Involve a neutral third party to help resolve it', 'D', 4, 'SOCIAL_SKILLS', 4);

-- EQ Q6 (id=66)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(66, 'Set clear goals and reward myself for progress', 'A', 5, 'MOTIVATION', 1),
(66, 'Think about the long-term benefits', 'B', 4, 'MOTIVATION', 2),
(66, 'Push through with discipline even when it is hard', 'C', 4, 'SELF_REGULATION', 3),
(66, 'Get inspiration from others who have succeeded', 'D', 3, 'MOTIVATION', 4);

-- EQ Q7 (id=67)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(67, 'Take a moment to process it before reacting', 'A', 5, 'SELF_REGULATION', 1),
(67, 'React emotionally and then try to calm down', 'B', 2, 'SELF_REGULATION', 2),
(67, 'Immediately look for solutions and next steps', 'C', 4, 'MOTIVATION', 3),
(67, 'Talk to someone I trust about how I feel', 'D', 4, 'SOCIAL_SKILLS', 4);

-- EQ Q8 (id=68)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(68, 'Stay true to my values and make independent decisions', 'A', 5, 'SELF_REGULATION', 1),
(68, 'Consider the group opinion but make my own choice', 'B', 4, 'SELF_AWARENESS', 2),
(68, 'Sometimes go along with the group to fit in', 'C', 2, 'SOCIAL_SKILLS', 3),
(68, 'Usually follow what most people are doing', 'D', 1, 'SELF_REGULATION', 4);

-- EQ Q9 (id=69)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(69, 'Adapt my communication style to work effectively with them', 'A', 5, 'SOCIAL_SKILLS', 1),
(69, 'Focus on our common goals rather than differences', 'B', 4, 'EMPATHY', 2),
(69, 'Find it challenging but try my best', 'C', 3, 'SELF_REGULATION', 3),
(69, 'Prefer to minimize interaction and focus on my tasks', 'D', 2, 'SELF_AWARENESS', 4);

-- EQ Q10 (id=70)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(70, 'Genuinely feel happy and express congratulations warmly', 'A', 5, 'EMPATHY', 1),
(70, 'Feel happy but sometimes compare with my own achievements', 'B', 3, 'SELF_AWARENESS', 2),
(70, 'Congratulate them politely', 'C', 3, 'SOCIAL_SKILLS', 3),
(70, 'Feel inspired and motivated to achieve more myself', 'D', 4, 'MOTIVATION', 4);

-- EQ Q11 (id=71)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(71, 'Immediately take responsibility and apologize sincerely', 'A', 5, 'SELF_AWARENESS', 1),
(71, 'Feel guilty and try to fix it without drawing attention', 'B', 3, 'SELF_REGULATION', 2),
(71, 'Apologize and actively work to make things right', 'C', 5, 'SOCIAL_SKILLS', 3),
(71, 'Try to understand how my mistake affected others', 'D', 4, 'EMPATHY', 4);

-- EQ Q12 (id=72)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(72, 'Very comfortable - I express both positive and negative emotions appropriately', 'A', 5, 'SELF_AWARENESS', 1),
(72, 'Comfortable with positive emotions but struggle with negative ones', 'B', 3, 'SELF_REGULATION', 2),
(72, 'I prefer to keep my emotions private', 'C', 2, 'SELF_REGULATION', 3),
(72, 'I express emotions but sometimes in inappropriate ways', 'D', 2, 'SELF_AWARENESS', 4);

-- EQ Q13 (id=73)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(73, 'Stay calm and ask for specific feedback to understand their perspective', 'A', 5, 'SELF_REGULATION', 1),
(73, 'Feel hurt but try not to take it personally', 'B', 3, 'SELF_AWARENESS', 2),
(73, 'Defend my work with facts and evidence', 'C', 3, 'MOTIVATION', 3),
(73, 'Get upset and find it hard to let go', 'D', 1, 'SELF_REGULATION', 4);

-- EQ Q14 (id=74)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(74, 'Reflect on why I feel that way and try to reach out', 'A', 5, 'SELF_AWARENESS', 1),
(74, 'Find other activities or people to spend time with', 'B', 3, 'SELF_REGULATION', 2),
(74, 'Feel hurt but accept it and move on', 'C', 3, 'SELF_REGULATION', 3),
(74, 'Talk to the group about how I feel', 'D', 4, 'SOCIAL_SKILLS', 4);

-- EQ Q15 (id=75)
INSERT INTO answer_options (question_id, option_text, option_label, score_value, trait_code, display_order) VALUES
(75, 'Try to address it diplomatically and facilitate a conversation', 'A', 5, 'SOCIAL_SKILLS', 1),
(75, 'Acknowledge it internally but wait to see if it resolves itself', 'B', 3, 'SELF_AWARENESS', 2),
(75, 'Try to lighten the mood with humor or a positive topic', 'C', 4, 'SOCIAL_SKILLS', 3),
(75, 'Stay quiet and focus on my own work', 'D', 2, 'SELF_REGULATION', 4);
