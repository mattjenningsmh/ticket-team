import { useState } from 'react';
import { getEventsByPerformer, Event } from '../app/services/apiService';

const Performers = () => {
  const [input, setInput] = useState<string>('');
  const [events, setEvents] = useState<Event[]>([]);

  const handleSearch = async () => {
    const data = await getEventsByPerformer(input);
    setEvents(data);
  };

  return (
    <div>
      <h1>Search Events by Performer</h1>
      <input
        type="text"
        value={input}
        onChange={(e) => setInput(e.target.value)}
        placeholder="Enter performer name"
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

export default Performers;