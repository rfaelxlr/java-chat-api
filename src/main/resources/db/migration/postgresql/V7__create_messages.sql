CREATE TABLE messages (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   creation_date TIMESTAMP WITHOUT TIME ZONE,
   modified_date TIMESTAMP WITHOUT TIME ZONE,
   active BOOLEAN NOT NULL,
   content VARCHAR(1000) NOT NULL,
   author_id BIGINT,
   channel_id BIGINT,
   CONSTRAINT pk_messages PRIMARY KEY (id)
);

ALTER TABLE messages ADD CONSTRAINT FK_MESSAGES_ON_AUTHOR FOREIGN KEY (author_id) REFERENCES users (id);

ALTER TABLE messages ADD CONSTRAINT FK_MESSAGES_ON_CHANNEL FOREIGN KEY (channel_id) REFERENCES channels (id);