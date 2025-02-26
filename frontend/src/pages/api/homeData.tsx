import { NextApiRequest, NextApiResponse } from 'next';
import { getFeaturedEvents, getPopularCategories } from '../../app/services/apiService';

export default async function handler(req: NextApiRequest, res: NextApiResponse) {
  const featuredEvents = await getFeaturedEvents();
  const popularCategories = await getPopularCategories();
  res.status(200).json({ featuredEvents, popularCategories });
}