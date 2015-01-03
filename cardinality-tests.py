import unittest
import cardinality as est

class EstimateTest(unittest.TestCase):

	def test_linear_counter_estimate(self):
		counter = est.LinearCounter(10000);
		for i in range(10000):
			counter.offer(i+10)
		self.assertEqual(counter.estimate(), 10000)

if __name__ == '__main__':
	unittest.main();