-- initial

-- queue db
CREATE TABLE mail_queue (
                            id BIGSERIAL PRIMARY KEY,
                            source_service TEXT,
                            original_id INT,
                            payload JSONB NOT NULL,
                            status TEXT DEFAULT 'pending',
                            attempts INT DEFAULT 0,
                            created_at TIMESTAMP DEFAULT NOW(),
                            updated_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_queue_pending ON mail_queue(created_at) WHERE status = 'pending';

-- tables for clone info. from monolith and registerms
CREATE TABLE monolith_outbox (
                                 id INT,
                                 payload JSONB,
                                 created_at TIMESTAMP,
                                 PRIMARY KEY (id)
);

CREATE TABLE register_outbox (
                                 id INT,
                                 payload JSONB,
                                 created_at TIMESTAMP,
                                 PRIMARY KEY (id)
);

-- trigger
CREATE OR REPLACE FUNCTION forward_to_queue() RETURNS TRIGGER AS $$
DECLARE
src_svc TEXT := TG_ARGV[0];
BEGIN
    -- put to queue
INSERT INTO public.mail_queue (source_service, original_id, payload)
VALUES (src_svc, NEW.id, NEW.payload);

-- notify worker
PERFORM pg_notify('new_mail_event', '');

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- put triggers on clone tables
CREATE TRIGGER trg_forward_monolith
    AFTER INSERT ON public.monolith_outbox
    FOR EACH ROW EXECUTE FUNCTION forward_to_queue('Monolith');

CREATE TRIGGER trg_forward_register
    AFTER INSERT ON public.register_outbox
    FOR EACH ROW EXECUTE FUNCTION forward_to_queue('RegisterMS');

-- host=host.docker.internal or container name if docker
-- if local then localhost

CREATE SUBSCRIPTION sub_service_monolith
CONNECTION 'host=localhost port=5432 dbname=DiplomaMonolith user=postgres password=0000'
PUBLICATION pub_service_monolith;

CREATE SUBSCRIPTION sub_service_registerms
CONNECTION 'host=localhost port=5432 dbname=Diploma user=postgres password=0000'
PUBLICATION pub_service_registerms;