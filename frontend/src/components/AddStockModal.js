import React, { useState } from 'react';

const AddStockModal = ({ isOpen, onClose, onStockAdded }) => {
  const [symbol, setSymbol] = useState('');
  const [quantity, setQuantity] = useState('');
  const [purchasePrice, setPurchasePrice] = useState('');
  const [purchaseDate, setPurchaseDate] = useState('');
  const [action, setAction] = useState('buy'); // 'buy' or 'sell'

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (action === 'buy') {
      try {
        const response = await fetch('/api/portfolio/holdings', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            symbol: symbol,
            quantity: parseFloat(quantity),
            purchasePrice: purchasePrice ? parseFloat(purchasePrice) : 0,
            purchaseDate: purchaseDate,
          }),
        });

        if (response.ok) {
          console.log('Stock added successfully');
          onStockAdded(); // Call the refresh function
        } else {
          console.error('Failed to add stock');
          // Handle error, e.g., show an error message to the user
        }
      } catch (error) {
        console.error('Error adding stock:', error);
        // Handle network error
      }
    } else {
      // Handle sell action (not implemented yet)
      console.log({ action, symbol, quantity });
      onClose();
    }
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-gray-600 bg-opacity-50 overflow-y-auto h-full w-full z-50">
      <div className="relative top-20 mx-auto p-5 border w-96 shadow-lg rounded-md bg-white dark:bg-gray-800">
        <div className="mt-3 text-center">
          <h3 className="text-lg leading-6 font-medium text-gray-900 dark:text-white">
            {action === 'buy' ? 'Buy Stock' : 'Sell Stock'}
          </h3>
          <form onSubmit={handleSubmit} className="mt-2">
            <div className="flex justify-center my-4">
              <button
                type="button"
                onClick={() => setAction('buy')}
                className={`px-4 py-2 rounded-l-md ${action === 'buy' ? 'bg-blue-600 text-white' : 'bg-gray-200 dark:bg-gray-600 text-gray-800 dark:text-white'}`}
              >
                Buy
              </button>
              <button
                type="button"
                onClick={() => setAction('sell')}
                className={`px-4 py-2 rounded-r-md ${action === 'sell' ? 'bg-red-600 text-white' : 'bg-gray-200 dark:bg-gray-600 text-gray-800 dark:text-white'}`}
              >
                Sell
              </button>
            </div>
            <input
              type="text"
              placeholder="Symbol (e.g., AAPL)"
              value={symbol}
              onChange={(e) => setSymbol(e.target.value.toUpperCase())}
              className="w-full p-2 border rounded mb-4 bg-white dark:bg-gray-700 text-gray-800 dark:text-white"
            />
            <input
              type="number"
              placeholder="Quantity"
              value={quantity}
              onChange={(e) => setQuantity(e.target.value)}
              className="w-full p-2 border rounded mb-4 bg-white dark:bg-gray-700 text-gray-800 dark:text-white"
            />
            <input
              type="number"
              placeholder="Purchase Price"
              value={purchasePrice}
              onChange={(e) => setPurchasePrice(e.target.value)}
              className="w-full p-2 border rounded mb-4 bg-white dark:bg-gray-700 text-gray-800 dark:text-white"
            />
            <input
              type="date"
              placeholder="Purchase Date"
              value={purchaseDate}
              onChange={(e) => setPurchaseDate(e.target.value)}
              className="w-full p-2 border rounded mb-4 bg-white dark:bg-gray-700 text-gray-800 dark:text-white"
            />
            <div className="items-center gap-2 mt-3">
              <button
                type="submit"
                className="w-full px-4 py-2 bg-blue-600 text-white text-base font-medium rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
              >
                Submit
              </button>
              <button
                type="button"
                onClick={onClose}
                className="w-full px-4 py-2 mt-2 bg-gray-300 dark:bg-gray-600 text-gray-800 dark:text-white text-base font-medium rounded-md shadow-sm hover:bg-gray-400 dark:hover:bg-gray-500 focus:outline-none focus:ring-2 focus:ring-gray-500"
              >
                Cancel
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default AddStockModal;