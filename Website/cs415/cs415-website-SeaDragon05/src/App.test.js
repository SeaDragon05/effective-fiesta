import { render, screen } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import App from './App';

test('renders CS415 Landing Page', () => {
  render(
    <BrowserRouter>
      <App />
    </BrowserRouter>
  );
  const headingElement = screen.getByText(/CS415 Landing Page/i);
  expect(headingElement).toBeInTheDocument();
});
