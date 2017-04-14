match ()-[r]->() delete r;
match (n) delete n;

create (portfolio_v2               : Webservice  {name:'portfolio-v2'})
create (profile_v2                 : Webservice  {name:'profile-v2'})
create (questionnaire              : Webservice  {name:'questionnaire'})
create (portfolio_v1               : Webservice  {name:'portfolio-v1'})
create (profile_v1                 : Webservice  {name:'profile-v1'})
create (signup                     : Webservice  {name:'signup'})

create (customertool_block1_front  : Plugin      {name:'customertool-block1-front'})
create (customertool_block1_back   : Plugin      {name:'customertool-block1-bank'})
create (customertool_block2_front  : Plugin      {name:'customertool-block2-front'})
create (customertool_block2_back   : Plugin      {name:'customertool-block2-bank'})
create (customertool_block3_front  : Plugin      {name:'customertool-block3-front'})
create (customertool_block3_back   : Plugin      {name:'customertool-block3-bank'})

create (customertool               : Webapp      {name:'customertool'})

create (trade                      : Database    {name:'trade'})
create (customer                   : Database    {name:'customer'})
create (crm                        : Database    {name:'crm'})

create (rmq_ex_portfolio_create    : RMQExchange {name:'portfolio-create'})
create (rmq_ex_portfolio_update    : RMQExchange {name:'portfolio-update'})
create (rmq_ex_person_create       : RMQExchange {name:'person-create'})
create (rmq_ex_person_update       : RMQExchange {name:'person-update'})
create (rmq_ex_sales_update        : RMQExchange {name:'sales-update'})

create (rmq_q_portfolio_create     : RMQQueue    {name:'portfolio-create'})
create (rmq_q_portfolio_update     : RMQQueue    {name:'portfolio-update'})
create (rmq_q_person_create        : RMQQueue    {name:'person-create'})
create (rmq_q_person_update        : RMQQueue    {name:'person-update'})
create (rmq_q_sales_update         : RMQQueue    {name:'sales-update'})

create (bank_listener              : App         {name:'bank-listener'})
create (crm_listener               : App         {name:'crm-listener'})

create (bank_poller                : App         {name:'bank-poller'})
create (crm_poller                 : App         {name:'crm-poller'})

create (profile_v2)                -[:USES]->      (questionnaire)
create (portfolio_v2)              -[:USES]->      (questionnaire)
create (signup)                    -[:USES]->      (portfolio_v1)

create (portfolio_v2)              -[:USES]->      (trade)
create (portfolio_v1)              -[:USES]->      (trade)
create (profile_v2)                -[:USES]->      (trade)
create (profile_v1)                -[:USES]->      (trade)
create (signup)                    -[:USES]->      (trade)
create (questionnaire)             -[:USES]->      (trade)
create (questionnaire)             -[:USES]->      (customer)

create (bank_poller)               -[:USES]->      (trade)
create (crm_poller)                -[:USES]->      (crm)

create (bank_listener)             -[:USES]->      (portfolio_v2)
create (bank_listener)             -[:USES]->      (profile_v2)
create (crm_listener)              -[:USES]->      (crm)

create (rmq_ex_portfolio_create)   -[:PUBLISHES]-> (rmq_q_portfolio_create)
create (rmq_ex_portfolio_update)   -[:PUBLISHES]-> (rmq_q_portfolio_update)
create (rmq_ex_person_create)      -[:PUBLISHES]-> (rmq_q_person_create)
create (rmq_ex_person_update)      -[:PUBLISHES]-> (rmq_q_person_update)
create (rmq_ex_sales_update)       -[:PUBLISHES]-> (rmq_q_sales_update)

create (crm_listener)              -[:CONSUMES]->  (rmq_q_person_create)
create (crm_listener)              -[:CONSUMES]->  (rmq_q_portfolio_create)
create (crm_listener)              -[:CONSUMES]->  (rmq_q_person_update)
create (crm_listener)              -[:CONSUMES]->  (rmq_q_portfolio_update)
create (bank_listener)             -[:CONSUMES]->  (rmq_q_sales_update)

create (customertool_block1_front) -[:COMPOSES]-> (customertool)
create (customertool_block2_front) -[:COMPOSES]-> (customertool)
create (customertool_block3_front) -[:COMPOSES]-> (customertool)
create (customertool_block1_front) -[:USES]->     (customertool_block1_back)
create (customertool_block2_front) -[:USES]->     (customertool_block2_back)
create (customertool_block3_front) -[:USES]->     (customertool_block3_back)

create (customertool_block1_back)  -[:USES]->     (profile_v2)
create (customertool_block2_back)  -[:USES]->     (portfolio_v2)
create (customertool_block3_back)  -[:USES]->     (questionnaire)

create (bank_poller)               -[:PRODUCES]-> (rmq_ex_portfolio_update)
create (bank_poller)               -[:PRODUCES]-> (rmq_ex_person_update)
create (crm_poller)                -[:PRODUCES]-> (rmq_ex_sales_update)

create (signup)                    -[:PRODUCES]-> (rmq_ex_person_create)
create (portfolio_v1)              -[:PRODUCES]-> (rmq_ex_portfolio_create)