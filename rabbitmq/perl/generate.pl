#!/usr/bin/env perl

use warnings;
use Net::AMQP::RabbitMQ;

my $mq = Net::AMQP::RabbitMQ->new;

$mq->connect('rabbitmq', { user => 'guest', password => 'guest' });
$mq->channel_open(1);
$mq->exchange_declare(1, 'exchange', {exchange_type => 'fanout', durable => 0});

print "Generate message: 1000000\n";
$mq->publish(1, '', '1000000', {exchange => 'exchange'}, {delivery_mode => 2});

$mq->disconnect;