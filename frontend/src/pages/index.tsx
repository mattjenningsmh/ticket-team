'use client';

import { useEffect, useState } from 'react';
import axios from 'axios';
import { Category, Event } from '@/app/services/apiService';
import Link from 'next/link';
import Search from '@/app/components/search';
import { Calendar, MapPin } from 'lucide-react';
import "@/app/globals.css";

export default function Home() {
  const [data, setData] = useState<{ featuredEvents: Event[]; popularCategories: Category[] } | null>(null);

  useEffect(() => {
    axios.get('/api/homeData').then((res) => setData(res.data));
  }, []);

  if (!data) return (
    <div className="min-h-screen flex items-center justify-center">
      <div className="animate-pulse text-xl font-medium text-muted-foreground">Loading...</div>
    </div>
  );

  const { featuredEvents, popularCategories } = data;

  return (
    <div className="min-h-screen bg-background">
      <header className="border-b border-border backdrop-blur-sm bg-background/80 sticky top-0 z-50">
        <div className="container mx-auto px-4 py-4">
          <nav className="flex justify-between items-center">
            <Link href="/" className="text-2xl font-bold gradient-bg text-transparent bg-clip-text hover:opacity-80 transition-opacity">
              Ticket Team
            </Link>
            <ul className="flex space-x-8">
              <li><Link href="/events" className="nav-link text-primary hover:text-secondary">Events</Link></li>
              <li><Link href="/performers" className="nav-link text-primary hover:text-secondary">Performers</Link></li>
              <li><Link href="/venues" className="nav-link text-primary hover:text-secondary">Venues</Link></li>
            </ul>
          </nav>
        </div>
      </header>

      <main className="container mx-auto px-4 py-12">
        <div className="max-w-4xl mx-auto text-center mb-16">
          <h1 className="text-5xl font-bold mb-6 bg-clip-text text-transparent gradient-bg">
            Find Your Next Experience
          </h1>
          <p className="text-xl text-muted-foreground mb-8">
            Discover amazing events happening near you
          </p>
          <Search />
        </div>

        <section className="mb-16">
          <h2 className="text-3xl font-bold mb-8">Featured Events</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {Array.isArray(featuredEvents) ? (
              featuredEvents.map((event: Event) => (
                <Link href={`/events/${event.id}`} key={event.id}>
                  <div className="bg-card rounded-xl overflow-hidden border border-border card-hover">
                    <div className="p-6">
                      <h3 className="font-bold text-xl mb-4">{event.name}</h3>
                      <div className="space-y-2 text-muted-foreground">
                        <div className="flex items-center gap-2">
                          <Calendar className="h-4 w-4" />
                          <span>{event.date}</span>
                        </div>
                        <div className="flex items-center gap-2">
                          <MapPin className="h-4 w-4" />
                          <span>{event.venue}</span>
                        </div>
                      </div>
                    </div>
                  </div>
                </Link>
              ))
            ) : (
              <p className="text-muted-foreground">No featured events available.</p>
            )}
          </div>
        </section>

        <section>
          <h2 className="text-3xl font-bold mb-8">Popular Categories</h2>
          <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
            {Array.isArray(popularCategories) && popularCategories.map((category: Category) => (
              <Link href={`/categories/${category.name}`} key={category.name}>
                <div className="bg-card rounded-xl border border-border p-6 text-center card-hover">
                  <h3 className="font-semibold text-lg">{category.name}</h3>
                </div>
              </Link>
            ))}
          </div>
        </section>
      </main>

      <footer className="bg-secondary mt-24 border-t border-border">
        <div className="container mx-auto px-4 py-12">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-12">
            <div>
              <h3 className="font-bold text-xl mb-4">About Us</h3>
              <p className="text-muted-foreground">
                TicketTeam is your go-to platform for discovering and booking amazing events near you.
              </p>
            </div>
            <div>
              <h3 className="font-bold text-xl mb-4">Quick Links</h3>
              <ul className="space-y-2">
                <li><Link href="/faq" className="text-muted-foreground hover:text-primary transition-colors">FAQ</Link></li>
                <li><Link href="/terms" className="text-muted-foreground hover:text-primary transition-colors">Terms of Service</Link></li>
                <li><Link href="/privacy" className="text-muted-foreground hover:text-primary transition-colors">Privacy Policy</Link></li>
              </ul>
            </div>
            <div>
              <h3 className="font-bold text-xl mb-4">Contact Us</h3>
              <div className="space-y-2 text-muted-foreground">
                <p>Email: support@ticketteam.com</p>
                <p>Phone: (123) 456-7890</p>
              </div>
            </div>
          </div>
        </div>
      </footer>
    </div>
  )
}