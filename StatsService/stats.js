const express = require('express');
const mongoose = require('mongoose');
const dotenv = require("dotenv");
dotenv.config();


const app = express();
const PORT = process.env.PORT || 8200;



mongoose.connect(process.env.DATABASE_URL, {
  dbName: "eventTicket",
  useNewUrlParser: true,
  useUnifiedTopology: true,
});

const logSchema = new mongoose.Schema({
  action: String,
  method: String,
  url:String,
  _class:String,
},
{
  collection: "log",
});

const Log = mongoose.model('Log', logSchema);

mongoose.connection.on('connected', () => {
  console.log('Connected to MongoDB');
});

mongoose.connection.on('error', (err) => {
  console.error('MongoDB connection error:', err);
});

app.use(express.json());



app.get('/lastEndpoint', async (req, res) => {
  try {
    const lastLog = await Log.findOne({}).sort({ timestamp: -1 }); 

    if (lastLog) {
      res.json({ lastEndpoint: lastLog.action, method:lastLog.method, url:lastLog.url, class: lastLog._class });
    } else {
      res.json({ lastEndpoint: 'No endpoint logged yet' });
    }
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});


// most frequent endpoint
app.get('/mostFrequentEndpoint', async (req, res) => {
  try {
    const pogostKlic = await Log.aggregate([
      {
        $group: {
            _id: {action:'$action', url: '$url', method: '$method'},
          count: { $sum: 1 }
        }
      },
      {
        $sort: {
          count: -1
        }
      },
      {
        $limit: 1
      }
    ]);

    if (pogostKlic.length > 0) {
      res.json({
        pogostKlic: pogostKlic[0]._id,
        count: pogostKlic[0].count
      });
    } else {
      res.json({ pogostKlic: 'No endpoint logged yet' });
    }
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});


// Endpoint call counts
app.get('/counts', async (req, res) => {
  try {
    const endpointCallCounts = await Log.aggregate([
      {
        $group: {
            _id: {action:'$action', url: '$url', method: '$method' },
          count: { $sum: 1 }
        }
      },
      {
        $sort: {
          count: -1
        }
      }
    ]);

    if (endpointCallCounts.length > 0) {
      res.json(endpointCallCounts);
    } else {
      res.json({ message: 'No endpoint calls logged yet' });
    }
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});


app.listen(PORT, () => {
  console.log(`Server is running on http://localhost:${PORT}`);
});
