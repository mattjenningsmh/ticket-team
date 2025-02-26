import axios from 'axios';

const apiClient = axios.create({
  baseURL: process.env.NEXT_PUBLIC_API_URL || 'http://localhost:5001',
});

export interface Event {
  id: string;
  name: string;
  date: string;
  venue: string;
}

export const getEventById = async (id: string): Promise<Event> => {
  const response = await apiClient.get<Event>(`/ticket-team/events/${id}`);
  return response.data;
};

export const getEventsByPerformer = async (performer: string): Promise<Event[]> => {
  const response = await apiClient.get<Event[]>(`/ticket-team/performers/${performer}`);
  return response.data;
};

export const getEventsByCategory = async (category: string): Promise<Category> => {
  const encodedCategory = encodeURIComponent(category);
  const response = await apiClient.get<Category>(`/ticket-team/category/${encodedCategory}`);
  return response.data;
};

export const getEventsByVenue = async (venue: string): Promise<Event[]> => {
  // Encode the venue to handle special characters and spaces
  const encodedVenue = encodeURIComponent(venue);
  const response = await apiClient.get<Event[]>(`/ticket-team/venues/${encodedVenue}`);
  return response.data;
};

export const getPopularCategories = async (): Promise<Category[]> => {
  const response = await apiClient.get<Category[]>('/ticket-team/categories/popular');
  return response.data;
};

export interface Category {
  name: string;
  events: Event[];
}

export const getFeaturedEvents = async (): Promise<Event[]> => {
  const response = await apiClient.get<Event[]>('/ticket-team/events/featured');
  return response.data;
}