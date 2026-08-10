import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CareerTestCard } from '../career-test/career-test-card/career-test-card';
import { CareerTestButton } from '../career-test/career-test-button/career-test-button';

@Component({
  selector: 'app-home',
  imports: [RouterLink, CommonModule, CareerTestCard, CareerTestButton],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home {
  stats = [
    { number: '50,000+', label: 'Students Assessed' },
    { number: '500+', label: 'Partner Schools' },
    { number: '15+', label: 'Career Categories' },
    { number: '95%', label: 'Satisfaction Rate' },
  ];

  dimensions = [
    { icon: '&#9881;', name: 'Orientation Style', desc: 'Discover your preferred work environment, whether structured or flexible, independent or collaborative.' },
    { icon: '&#9733;', name: 'Interest', desc: 'Map your career interests across Realistic, Investigative, Artistic, Social, Enterprising, and Conventional domains.' },
    { icon: '&#9786;', name: 'Personality', desc: 'Understand your personality traits including openness, conscientiousness, extraversion, and agreeableness.' },
    { icon: '&#9889;', name: 'Aptitude', desc: 'Evaluate your cognitive strengths in numerical, verbal, logical, and spatial reasoning.' },
    { icon: '&#9829;', name: 'Emotional Quotient', desc: 'Measure your emotional intelligence including self-awareness, empathy, and social skills.' },
  ];

  steps = [
    { number: '01', title: 'Register & Start Assessment', desc: 'Create your account and begin the 5-dimensional career assessment designed by experts.' },
    { number: '02', title: 'Answer Assessment Questions', desc: 'Complete questions across 5 categories: Orientation, Interest, Personality, Aptitude, and EQ.' },
    { number: '03', title: 'Get Your Career Report', desc: 'Receive a detailed PDF report with charts, scores, and top 5 career recommendations.' },
  ];

  testimonials = [
    { name: 'Priya Sharma', role: 'Class 12 Student', text: 'The assessment helped me discover my true strengths. I now know exactly which career path suits me best!' },
    { name: 'Rahul Gupta', role: 'Engineering Graduate', text: 'The detailed report with career recommendations was incredibly insightful. Highly recommend to all students.' },
    { name: 'Ananya Patel', role: 'Class 10 Student', text: 'I was confused about stream selection. This platform made my decision so much easier with clear guidance.' },
  ];
}
