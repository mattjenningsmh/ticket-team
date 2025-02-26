import { useState } from 'react';
import { getEventsByCategory, Event } from '../app/services/apiService';

const Categories = () => {
  const [input, setInput] = useState<string>('');
  const [events, setEvents] = useState<Event[]>([]);

  const handleSearch = async () => {
    const data = await getEventsByCategory(input);
    setEvents(data);
  };

  return (
    <div>
      <h1>Search Events by Category</h1>
      <input
        type="text"
        value={input}
        onChange={(e) => setInput(e.target.value)}
        placeholder="Enter category"
      />
      <button onClick={handleSearch}>Search</button>
      <ul>
        {events.map((event) => (
          <li key={event.id}>{event.name}</li>
        ))}
      </ul>
    </div>
  );
};

export default Categories;