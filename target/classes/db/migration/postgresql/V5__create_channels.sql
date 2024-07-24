CREATE TABLE channels (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   creation_date TIMESTAMP WITHOUT TIME ZONE,
   modified_date TIMESTAMP WITHOUT TIME ZONE,
   active BOOLEAN NOT NULL,
   name VARCHAR(100) NOT NULL,
   size INTEGER NOT NULL,
   guild_id BIGINT,
   CONSTRAINT pk_channels PRIMARY KEY (id)
);

ALTER TABLE channels ADD CONSTRAINT FK_CHANNELS_ON_GUILD FOREIGN KEY (guild_id) REFERENCES guilds (id);

ALTER TABLE channels
ADD CONSTRAINT unique_channels_guild_name UNIQUE (guild_id, name);