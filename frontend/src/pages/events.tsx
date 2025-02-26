import { useState } from 'react';
import { getEventById, Event } from '../app/services/apiService';

const Events = () => {
  const [input, setInput] = useState<string>('');
  const [event, setEvent] = useState<Event | null>(null);

  const handleSearch = async () => {
    const data = await getEventById(input);
    setEvent(data);
  };

  return (
    <div>
      <h1>Search Event by ID</h1>
      <input
        type="text"
        value={input}
        onChange={(e) => setInput(e.target.value)}
        placeholder="Enter event ID"
      />
      <button onClick={handleSearch}>Search</button>
      {event && (
        <div>
          <h2>{event.name}</h2>
          {/* Display other event details */}
        </div>
      )}
    </div>
  );
};

export default Events;