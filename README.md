# Technical Task Description


As a BE developer, you are requested to enhance the efficiency of our Web API usage by preventing abuse caused 
by excessive requests from multiple users (quota). To achieve this, you need to implement a robust access-limiting 
mechanism that ensures optimal performance and resource utilization.

The new API should accept up to X api requests per User (for instance - 5 requests per User).
Beyond the quota of X requests - the User should be blocked.

Unfortunately, our users reside in 2 different sources, one in elastic and one in MySql
During the day 9:00 - 17:00 (UTC) we use MySql as a source
During the night 17:01 - 8:59 we use Elastic

* Create a new spring boot application (can be run and tested)
* In your coding consider scalability, extensibility and design patterns
* You should implement one db (as localhost), and the other implementation can be printing functions only.
* Bonus : add unit testing for your solution.
* Do not use 3rd party libraries for the quota mechanism.

**Model:**
`public class User { private String id; private String firstName; private String lastName; private LocalDateTime lastLoginTimeUtc; }`

**Api functions:**
* CRUD function for User model
* consumeQuota(@PathVariable String userId); this function is the main function used for access and counting the users’ quotas
* getUsersQuota(); this function returns all users and quotas statuses
* *** Bonus : Add unit testing for your solution
